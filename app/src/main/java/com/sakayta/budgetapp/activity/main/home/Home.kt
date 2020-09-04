package com.sakayta.budgetapp.activity.main.home

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sakayta.budgetapp.R
import com.sakayta.budgetapp.activity.main.HomeViewModel
import com.sakayta.budgetapp.model.Account
import com.sakayta.budgetapp.util.Constants
import com.sakayta.budgetapp.util.Resource
import kotlinx.android.synthetic.main.home_fragment.*
import java.lang.ClassCastException

class Home : Fragment(),HomeAdapter.HomeAdapterListener {

    private lateinit var viewModel: HomeViewModel
    private  lateinit var adapter:HomeAdapter;

    private  lateinit var mlistener:HomeInteractionListener

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



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)


        adapter = HomeAdapter(this)

        recycler.layoutManager=LinearLayoutManager(context)
        recycler.adapter = adapter

        viewModel.loadAccounts()
        viewModel.getAccountWithExpense()
        observeAcountList()
        showNoData(1)

        fab.setOnClickListener{
            findNavController().navigate(R.id.action_home2_to_addAcount)
        }


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
