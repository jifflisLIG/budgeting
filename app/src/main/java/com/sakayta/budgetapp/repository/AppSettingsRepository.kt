package com.sakayta.budgetapp.repository

import com.sakayta.budgetapp.model.AppSettings
import com.sakayta.budgetapp.persistence.AppSettingDao
import com.sakayta.budgetapp.util.JobManager
import kotlinx.coroutines.*


class AppSettingsRepository(val appSettingDao:AppSettingDao)
    :JobManager("AppSettingsRepository") {

    suspend fun getAppSettings():AppSettings{
        return GlobalScope.async (Dispatchers.IO){
            return@async appSettingDao.getAppSettings()
        }.await()
    }

    fun save(appSettings: AppSettings){
        GlobalScope.launch {
            appSettingDao.insert(appSettings)
        }
    }

}