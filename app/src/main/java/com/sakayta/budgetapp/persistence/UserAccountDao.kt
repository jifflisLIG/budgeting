package com.sakayta.budgetapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import com.sakayta.budgetapp.model.UserAccount


@Dao
interface UserAccountDao {
    @Insert
    suspend fun insert(userAccount: UserAccount)
}