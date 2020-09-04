package com.sakayta.budgetapp.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sakayta.budgetapp.model.*

@Database(entities = [
    Account::class,
    Expenses::class,
    AppSettings::class,
    User::class,
    UserPayload::class,
    UserAccount::class

], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getAccountDao(): AccountDao
    abstract fun getExpensesDao(): ExpensesDao
    abstract fun getAppSettingDao():AppSettingDao
    abstract  fun  getUserDao():UserDao
    abstract  fun getUserPayloadDao():UserPayloadDao
    abstract  fun getUserAccountDao():UserAccountDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "budget.sqlite"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}








