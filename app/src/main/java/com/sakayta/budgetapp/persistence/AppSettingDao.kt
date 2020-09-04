package com.sakayta.budgetapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sakayta.budgetapp.model.AppSettings


@Dao
interface AppSettingDao {

    @Query("Select * from app_settings")
    suspend fun getAppSettings():AppSettings

    @Insert
    suspend fun insert(appSettings: AppSettings)


}