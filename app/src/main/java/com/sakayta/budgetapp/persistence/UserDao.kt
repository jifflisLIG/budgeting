package com.sakayta.budgetapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sakayta.budgetapp.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user:User);

    @Query("Select * from user where user_id = :user_id")
    suspend fun getUser(user_id:String):User

    @Query("Select * from user")
    suspend fun getUsers():List<User>

    @Query("Select * from user where username = :username")
    suspend fun getUserByUsername(username:String):User


}