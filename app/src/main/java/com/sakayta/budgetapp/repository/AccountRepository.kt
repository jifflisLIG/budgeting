package com.sakayta.budgetapp.repository

import com.sakayta.budgetapp.model.Account
import com.sakayta.budgetapp.model.AcountWithTransaction
import com.sakayta.budgetapp.persistence.AccountDao
import com.sakayta.budgetapp.util.JobManager
import kotlinx.coroutines.*

class AccountRepository(val accountDao: AccountDao):
    JobManager("NoteRepository") {

    companion object{
        const val PARENTJOB = "ParentJob"
    }

    init {
        addJob(PARENTJOB, Job())
    }

    suspend fun getAccounts():List<Account>{
        return GlobalScope.async (Dispatchers.IO){
            return@async accountDao.getAllAcounts(page = 1,pageSize = 10)
         }.await()
    }

    suspend fun getAcountAndExpense():List<AcountWithTransaction>{
        return GlobalScope.async (Dispatchers.IO){
            return@async accountDao.getAcountAndExpense()
        }.await()
    }

    fun insert(account:Account){
        GlobalScope.launch(Dispatchers.IO) {
            accountDao.insert(account);
        }
    }

    fun update(account:Account){
        GlobalScope.launch(Dispatchers.IO) {
            accountDao.update(account);
        }
    }

    fun delelete(account: Account){
        GlobalScope.launch(Dispatchers.IO) {
            accountDao.delete(account.account_id)
        }
    }

}