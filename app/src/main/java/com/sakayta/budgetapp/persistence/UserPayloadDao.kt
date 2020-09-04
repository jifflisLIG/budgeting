package com.sakayta.budgetapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sakayta.budgetapp.model.UserPayload

@Dao
interface UserPayloadDao{

    @Insert
    suspend fun insert(userPayload: UserPayload)

//    @Query("Select * from ")
//    suspend fun
}