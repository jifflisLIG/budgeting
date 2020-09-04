package com.sakayta.budgetapp.repository

import com.sakayta.budgetapp.model.Account
import com.sakayta.budgetapp.model.Expenses
import com.sakayta.budgetapp.persistence.AccountDao
import com.sakayta.budgetapp.persistence.AppDatabase
import com.sakayta.budgetapp.persistence.ExpensesDao
import com.sakayta.budgetapp.util.JobManager
import kotlinx.coroutines.*

class ExpensesRepository(val expenseDao: ExpensesDao):

    JobManager("ExpensesRepository") {

    companion object{
        const val PARENTJOB = "ParentJob"
    }

    init {
        addJob(PARENTJOB, Job())
    }


    fun insert(expense:Expenses){
        GlobalScope.launch(Dispatchers.IO) {
            expenseDao.insert(expense);
        }
    }


}