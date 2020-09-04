package com.sakayta.budgetapp.repository

import com.sakayta.budgetapp.model.AppSettings
import com.sakayta.budgetapp.model.User
import com.sakayta.budgetapp.persistence.AppSettingDao
import com.sakayta.budgetapp.persistence.UserDao
import com.sakayta.budgetapp.util.JobManager
import kotlinx.coroutines.*


class UserRepository(val userDao:UserDao)
    :JobManager("UserRepository") {


    suspend fun getUserById(id:String):User{
        return GlobalScope.async (Dispatchers.IO){
            return@async userDao.getUser(id)
        }.await()
    }

    suspend fun getUserByUsername(username:String):User{
        return GlobalScope.async (Dispatchers.IO){
            return@async userDao.getUser(username)
        }.await()
    }


    suspend fun getUser(id:Int):List<User>{
        return GlobalScope.async (Dispatchers.IO){
            return@async userDao.getUsers()
        }.await()
    }


    fun save(user: User){
        GlobalScope.launch {
            userDao.insert(user)
        }
    }

}