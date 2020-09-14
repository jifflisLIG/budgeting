package com.sakayta.budgetapp.repository

import com.sakayta.budgetapp.model.Account
import com.sakayta.budgetapp.model.AccountLedger
import com.sakayta.budgetapp.model.AcountWithTransaction
import com.sakayta.budgetapp.persistence.AccountDao
import com.sakayta.budgetapp.persistence.AccountLedgerDao
import com.sakayta.budgetapp.util.JobManager
import kotlinx.coroutines.*

class AccountLedgerRepository(val accountLedgerDao: AccountLedgerDao):
    JobManager("NoteRepository") {

    companion object{
        const val PARENTJOB = "ParentJob"
    }

    init {
        addJob(PARENTJOB, Job())
    }


    fun insert(account:AccountLedger){
        GlobalScope.launch(Dispatchers.IO) {
            accountLedgerDao.insert(account);
        }
    }

    fun update(account:AccountLedger){
        GlobalScope.launch(Dispatchers.IO) {
            accountLedgerDao.update(account);
        }
    }

    fun delelete(account: AccountLedger){
        GlobalScope.launch(Dispatchers.IO) {
            accountLedgerDao.delete(account.account_id)
        }
    }

}