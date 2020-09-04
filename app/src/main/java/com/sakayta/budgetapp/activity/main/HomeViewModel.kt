package com.sakayta.budgetapp.activity.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sakayta.budgetapp.model.Account
import com.sakayta.budgetapp.model.Expenses
import com.sakayta.budgetapp.persistence.AppDatabase
import com.sakayta.budgetapp.repository.AccountRepository
import com.sakayta.budgetapp.repository.ExpensesRepository
import com.sakayta.budgetapp.util.Resource
import kotlinx.coroutines.*


class HomeViewModel(application: Application) : AndroidViewModel(application) {


    private val accountRepository:AccountRepository =
        AccountRepository(AppDatabase.getDatabase(application).getAccountDao())

    private val expensesRepository:ExpensesRepository =
        ExpensesRepository(AppDatabase.getDatabase(application).getExpensesDao())

    var accountList:MutableLiveData<Resource<ArrayList<Account>>> = MutableLiveData()

    fun observeAccoutList():LiveData<Resource<ArrayList<Account>>>{
        return accountList;
    }

    fun loadAccounts(){
        accountList.value = Resource.Loading();
        GlobalScope.launch (Dispatchers.Main){
            accountList.value = Resource.Success(
                data=accountRepository.getAccounts() as ArrayList<Account>
            );
        }
    }

    fun getAccountWithExpense(){
        GlobalScope.launch(Dispatchers.Main) {
            accountRepository.getAcountAndExpense()
        }
    }

    fun addExpenses(expenses:Expenses){
        expensesRepository.insert(expenses)
    }

    fun updateAccount(account: Account){
        accountRepository.update(account)
    }

    fun addAccount(acount:Account){
        accountRepository.insert(account =acount)
    }

    private fun addAccountList(account:Account){
        val accounts = accountList.value!!.data!!
        accounts.add(account)
        accountList.value = Resource.Success(data = accounts)
    }



    fun delete(account: Account) {
        accountRepository.delelete(account)
        val accounts = accountList.value!!.data
        accounts!!.remove(account)
        accountList.value = Resource.Success(data = accounts)
    }

}
