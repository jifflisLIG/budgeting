package com.sakayta.budgetapp.activity.main.add_expense

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.sakayta.budgetapp.R
import com.sakayta.budgetapp.activity.main.HomeViewModel
import com.sakayta.budgetapp.model.Account
import com.sakayta.budgetapp.model.Expenses
import com.sakayta.budgetapp.util.Constants.Companion.ACCOUNT_KEY
import com.sakayta.budgetapp.util.Date
import com.sakayta.budgetapp.util.Numb.Companion.formatDecimalWithComma
import kotlinx.android.synthetic.main.fragment_add_expense.*

class AddExpense : Fragment() {

    lateinit var account: Account
    private lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val account = requireArguments().getParcelable(ACCOUNT_KEY) as Account?
        this.account = account!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =    ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        setView(account)

        submit.setOnClickListener{

            if(TextUtils.isEmpty(expense.text.toString())){
                Toast.makeText(context,"Please Input value",Toast.LENGTH_LONG).show()
            }else{

                val amount = expense.text.toString().toDouble()

                account.amount_expense = account.amount_expense + amount
                account.amount_balance = account.amount_balance - amount

                var expenses = Expenses(
                    acount_id = account.account_id,
                    amount = amount,
                    remarks = remarks.text.toString(),
                    date = Date.currentDateTime())

                viewModel.addExpenses(expenses)
                viewModel.updateAccount(account)

                setView(account)
                expense.setText("")
                remarks.setText("")
                expense.requestFocus()
            }

        }
    }

    private fun setView(account:Account){
        balance.text = formatDecimalWithComma(account.amount_balance)
        lblAccount.text = account.acount_name
        val details= " ${formatDecimalWithComma(account.amount_budget)} budget, expense is ${formatDecimalWithComma(account.amount_expense)}"
        description.text =details
    }


}
