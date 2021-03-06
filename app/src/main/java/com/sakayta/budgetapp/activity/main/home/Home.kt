package com.sakayta.budgetapp.activity.main.home

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sakayta.budgetapp.R
import com.sakayta.budgetapp.activity.main.HomeViewModel
import com.sakayta.budgetapp.model.Account
import com.sakayta.budgetapp.util.Constants
import com.sakayta.budgetapp.util.Resource
import com.sakayta.budgetapp.util.SimpleDividerItemDecoration
import kotlinx.android.synthetic.main.home_fragment.*
import java.text.DecimalFormat

class Home : Fragment(),HomeAdapter.HomeAdapterListener {

    private lateinit var viewModel: HomeViewModel
    private  lateinit var adapter:HomeAdapter;

    private  lateinit var mlistener:HomeInteractionListener
    var decimalFormat: DecimalFormat = DecimalFormat("#,###.00")

    interface HomeInteractionListener{
        fun isLoading(flag:Boolean)
    }

    override fun onItemClick(position: Int) {
        val account = adapter.getAccount(position)
        val bundle = bundleOf(Constants.ACCOUNT_KEY to account)
        findNavController().navigate(R.id.action_home2_to_addExpense,bundle)
    }


    override fun modify(position: Int) {
        val account = adapter.getAccount(position)
        val bundle = bundleOf(Constants.ACCOUNT_KEY to account)
        findNavController().navigate(R.id.action_home2_to_addAcount,bundle)
    }


    override fun delete(position: Int) {
        val account = adapter.getAccount(position)
       var dialog:AlertDialog.Builder = AlertDialog.Builder(context)
           .setMessage("Are you sure you want to delete this account?")
           .setPositiveButton("Ok",DialogInterface.OnClickListener {
                   dialog, which ->
                    viewModel.delete(account);

           })
           .setNegativeButton("Cancel",DialogInterface.OnClickListener{
               dialog, which ->
               dialog.dismiss()
           })

        dialog.show()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is HomeInteractionListener){
            mlistener = context
        }else{
            throw ClassCastException("HomeIntereactionListener must be implemented!")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.home_fragment, container, false);
        return view
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        adapter = HomeAdapter(this)
        recycler.layoutManager=LinearLayoutManager(context)
        recycler.adapter = adapter
        recycler.addItemDecoration(SimpleDividerItemDecoration(this.context))
        viewModel.initialize()

        viewModel.getAccountDetails()
        viewModel.loadData()


        observeAcountList()
        observeAccountRefresher()
        observeAccountDetails()

        showNoData(1)

        fab.setOnClickListener{
            findNavController().navigate(R.id.action_home2_to_addAcount)
        }


    }


    private fun observeAccountDetails(){
        viewModel.observeAccountDetails().observe(viewLifecycleOwner, Observer {resource->
            when(resource){

                is Resource.Loading->{
                    budget.text = "..."
                    expenses.text = "..."
                    balance.text = "..."
                }

                is Resource.Error ->{
                    budget.text = "...!"
                    expenses.text = "...!"
                    balance.text = "Error!"
                }

                is Resource.Success ->{
                    val accountDetails = resource.data
                    budget.text = decimalFormat.format(accountDetails!!.budget)
                    expenses.text = decimalFormat.format(accountDetails!!.expenses)
                    balance.text = decimalFormat.format(accountDetails!!.balance)
                }

            }
        })

    }

    private fun observeAcountList(){
        viewModel.observeAccoutList().observe(viewLifecycleOwner, Observer {resource->
            when(resource){

                is Resource.Loading->{
                    mlistener.isLoading(true)
                }

                is Resource.Error ->{
                    mlistener.isLoading(false)
                }

                is Resource.Success ->{
                    mlistener.isLoading(false)
                    loadAccounts(resource.data)
                    showNoData(resource.data!!.size)
                }

            }
        })

    }


    private fun observeAccountRefresher(){
        viewModel.observeAcountRefresher().observe(viewLifecycleOwner, Observer {resource->
            when(resource){

                is Resource.Loading->{
                    mlistener.isLoading(true)
                }

                is Resource.Error ->{
                    mlistener.isLoading(false)
                }

                is Resource.Success ->{
                    mlistener.isLoading(false)
                }

            }
        })
    }


    fun loadAccounts(accounts:ArrayList<Account>?){
        adapter.accountList = accounts
    }

    fun showNoData(size:Int){
        if(size==0){
            noData.visibility = View.VISIBLE
        }
        else{
            noData.visibility = View.GONE
        }
    }

}
