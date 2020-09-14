package com.sakayta.budgetapp.activity.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sakayta.budgetapp.model.Account
import com.sakayta.budgetapp.model.Expenses
import com.sakayta.budgetapp.model.User
import com.sakayta.budgetapp.persistence.AppDatabase
import com.sakayta.budgetapp.repository.AccountRepository
import com.sakayta.budgetapp.repository.ExpensesRepository
import com.sakayta.budgetapp.repository.UserRepository
import com.sakayta.budgetapp.util.AccountRefresher
import com.sakayta.budgetapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var user: User

    private val userRepository: UserRepository =
        UserRepository(AppDatabase.getDatabase(application).getUserDao())

    private val accountRepository: AccountRepository =
        AccountRepository(AppDatabase.getDatabase(application).getAccountDao())

    private val expensesRepository: ExpensesRepository =
        ExpensesRepository(AppDatabase.getDatabase(application).getExpensesDao())

    private var accountList: MutableLiveData<Resource<ArrayList<Account>>> = MutableLiveData()

    private var accountDetails: MutableLiveData<Resource<AccountDetails>> = MutableLiveData()

    private var isAccountRefreshing: MutableLiveData<Resource<Boolean>> = MutableLiveData()


    fun insertUser() {
        userRepository.save(
            User(
                id = 1,
                user_id = "322413521053452",
                email = "jifflisotomier@gmail.dom",
                username = "Jifflis",
                password = "pass1234",
                pic_path = "",
                refreshDate = "2020-10-01"
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initialize() {
        accountList.value = Resource.Loading();
        GlobalScope.launch(Dispatchers.Main) {
            user = userRepository.getUserById("322413521053452");
            AccountRefresher().initialize(getApplication(), user)
            isAccountRefreshing.value = Resource.Success(data = true);
        }
    }

    fun observeAccountDetails(): LiveData<Resource<AccountDetails>> {
        return accountDetails;
    }


    fun observeAccoutList(): LiveData<Resource<ArrayList<Account>>> {
        return accountList;
    }

    fun observeAcountRefresher(): LiveData<Resource<Boolean>> {
        return isAccountRefreshing;
    }

    fun loadData() {
        accountList.value = Resource.Loading();
        GlobalScope.launch(Dispatchers.Main) {

            accountList.value = Resource.Success(
                data = accountRepository.getAccounts() as ArrayList<Account>
            )

            accountRepository.getAcountAndExpense()
        }
    }

    fun getAccountDetails() {
        accountDetails.value = Resource.Loading()
        GlobalScope.launch(Dispatchers.Main) {

            val balance :Double
            val expenses: Double
            val budget : Double

            try {
                 balance = accountRepository.getTotalBalance()
                 expenses = accountRepository.getTotalExpenses()
                 budget = accountRepository.getTotalBudget()

                accountDetails.value = Resource.Success(
                    data = AccountDetails(
                        balance = balance,
                        expenses = expenses,
                        budget = budget
                    )
                )
            }catch (ex:Exception){
                accountDetails.value =Resource.Error(message = ex.toString())
            }

        }
    }


    fun addExpenses(expenses: Expenses) {
        expensesRepository.insert(expenses)
    }

    fun updateAccount(account: Account) {
        accountRepository.update(account)
    }

    fun addAccount(acount: Account) {
        accountRepository.insert(account = acount)
    }

    private fun addAccountList(account: Account) {
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

class AccountDetails(
    val balance: Double = 0.0,
    val expenses: Double = 0.0,
    val budget: Double = 0.0
)