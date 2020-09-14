package com.sakayta.budgetapp.util

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.sakayta.budgetapp.model.Account
import com.sakayta.budgetapp.model.AccountLedger
import com.sakayta.budgetapp.model.User
import com.sakayta.budgetapp.persistence.AppDatabase
import com.sakayta.budgetapp.repository.AccountLedgerRepository
import com.sakayta.budgetapp.repository.AccountRepository
import com.sakayta.budgetapp.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AccountRefresher {

    init {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initialize(application: Application, user: User) {
        val currentDate = Date.currentDate()
        val date = Date.stringToDate(user.refreshDate)

        if (currentDate.isEqual(date) ||
            currentDate.isAfter(date)
        ) {
            refresh(application, user)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun refresh(application: Application, user: User) {
        val dbase = AppDatabase.getDatabase(application)

        updateAccounts(dbase, user)
        updateUser(dbase, user)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUser(dbase: AppDatabase, user: User) {
        val userRepo = UserRepository(dbase.getUserDao())
        val date = Date.stringToDate(user.refreshDate).plusMonths(1)
        user.refreshDate = date.toString()
        userRepo.update(user)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateAccounts(dbase: AppDatabase, user: User) {
        val accountRepo = AccountRepository(dbase.getAccountDao())
        val accountLedgetRepo = AccountLedgerRepository(dbase.getAccountLedgertDao())

        GlobalScope.launch {
            val date = Date.stringToDate(user.refreshDate)
            val accounts: List<Account> = accountRepo.getAccounts()

            for (account in accounts) {

                val account_id = account.account_id
                val beginning_balance = account.amount_budget
                val ending_balance: Double = account.amount_budget - account.amount_expense
                val amount_expense: Double = account.amount_expense
                val amount_budget: Double = account.amount_budget
                val amount_adjusted: Double = account.amount_adjusted
                val status = 0
                val month = date.month.value
                val year = date.year


                val accountLedger = AccountLedger(
                    id = 0,
                    account_id = account_id,
                    beginning_balance = beginning_balance,
                    ending_balance = ending_balance,
                    amount_expense = amount_expense,
                    amount_budget = amount_budget,
                    amount_adjusted = amount_adjusted,
                    status = status,
                    month = month,
                    year = year
                )

                account.amount_expense = 0.0
                account.amount_adjusted = 0.0
                account.amount_balance = account.amount_budget

                accountLedgetRepo.insert(accountLedger)
                accountRepo.update(account)
            }
        }

    }


}