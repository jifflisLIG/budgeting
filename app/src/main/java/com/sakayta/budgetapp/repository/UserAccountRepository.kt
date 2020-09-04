package com.sakayta.budgetapp.repository

import com.sakayta.budgetapp.model.UserAccount
import com.sakayta.budgetapp.model.UserPayload
import com.sakayta.budgetapp.persistence.UserAccountDao
import com.sakayta.budgetapp.util.JobManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserAccountRepository(
    val userAccountDao: UserAccountDao
):JobManager("UserAccountRepository") {

    fun save(userAccount: UserAccount){
        GlobalScope.launch {
            userAccountDao.insert(userAccount)
        }
    }


}