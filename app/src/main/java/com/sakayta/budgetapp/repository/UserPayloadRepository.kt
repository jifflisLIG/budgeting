package com.sakayta.budgetapp.repository

import com.sakayta.budgetapp.model.AppSettings
import com.sakayta.budgetapp.model.UserPayload
import com.sakayta.budgetapp.persistence.AppSettingDao
import com.sakayta.budgetapp.persistence.UserPayloadDao
import com.sakayta.budgetapp.util.JobManager
import kotlinx.coroutines.*


class UserPayloadRepository(val userPayloadDao:UserPayloadDao)
    :JobManager("UserPayloadRepository") {


    fun save(userPayload: UserPayload){
        GlobalScope.launch {
            userPayloadDao.insert(userPayload)
        }
    }

}