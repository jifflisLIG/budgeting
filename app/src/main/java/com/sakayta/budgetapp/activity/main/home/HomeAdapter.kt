package com.sakayta.budgetapp.activity.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.sakayta.budgetapp.R
import com.sakayta.budgetapp.model.Account
import com.sakayta.budgetapp.util.Numb.Companion.formatDecimalWithComma
import kotlinx.android.synthetic.main.list_account.view.*

class HomeAdapter(
    val adapterListener:HomeAdapterListener
):RecyclerView.Adapter<HomeAdapter.ViewHolder>(){

    var accountList:ArrayList<Account>? = ArrayList()

    // setter
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    interface HomeAdapterListener{
       fun onItemClick(position:Int)
       fun modify(position: Int)
       fun delete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_account,
                parent,
                false),
            adapterListener
        )
    }

    override fun getItemCount(): Int {
        return  accountList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(accountList!![position]);
    }

    fun addAll(accounts:ArrayList<Account>){
        this.accountList = accounts
        notifyDataSetChanged()
    }

    fun getAccount(position:Int):Account{
      return accountList!![position];
    }

    class ViewHolder(
        private val v: View,
        private val adapterListener: HomeAdapterListener

    ): RecyclerView.ViewHolder(v),View.OnClickListener {

        fun bind(account:Account){

            v.acount_name.text = account.acount_name
            v.budget.text = formatDecimalWithComma(account.amount_budget)
            v.expense.text = formatDecimalWithComma(account.amount_expense)
            v.balance.text = formatDecimalWithComma(account.amount_balance)
            v.popMenu.setOnClickListener(this)
            v.container.setOnClickListener(this)

        }

        override fun onClick(view: View?) {

            if(view!!.id==R.id.popMenu){
                val popup = PopupMenu(view.context, view)
                popup.inflate(R.menu.budgets_option_menu)
                popup.setOnMenuItemClickListener { item ->

                    if(item.itemId==R.id.modify){
                        adapterListener.modify(bindingAdapterPosition)
                    }

                    if(item.itemId==R.id.delete){
                        adapterListener.delete(bindingAdapterPosition)
                    }

                    false
                }
                popup.show()
            }

            if(view.id==R.id.container){
                adapterListener.onItemClick(bindingAdapterPosition)
            }
        }

    }
}