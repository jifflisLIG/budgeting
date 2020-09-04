package com.sakayta.budgetapp.activity.intro

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sakayta.budgetapp.model.AppSettings
import com.sakayta.budgetapp.model.User
import com.sakayta.budgetapp.model.UserAccount
import com.sakayta.budgetapp.model.UserPayload
import com.sakayta.budgetapp.persistence.AppDatabase
import com.sakayta.budgetapp.persistence.UserDao
import com.sakayta.budgetapp.repository.AppSettingsRepository
import com.sakayta.budgetapp.repository.UserAccountRepository
import com.sakayta.budgetapp.repository.UserPayloadRepository
import com.sakayta.budgetapp.repository.UserRepository
import com.sakayta.budgetapp.util.Date
import com.sakayta.budgetapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class IntroViewModel(application: Application): AndroidViewModel(application) {

    var dbase = AppDatabase.getDatabase(application);

    private val appSettingRepository: AppSettingsRepository =
        AppSettingsRepository(dbase.getAppSettingDao())

    private val userRepository:UserRepository =
        UserRepository(dbase.getUserDao())

    private val userAccountRepository:UserAccountRepository =
        UserAccountRepository(dbase.getUserAccountDao())

    private val userPayloadRepository:UserPayloadRepository =
        UserPayloadRepository(dbase.getUserPayloadDao())



    private var fragments:MutableLiveData<Resource<List<Fragment>>> =
        MutableLiveData();

    fun observeFragments():LiveData<Resource<List<Fragment>>>{
        return  fragments;
    }

    fun getFragments(){
        fragments.value = Resource.Loading()
        GlobalScope.launch (Dispatchers.Main){

              val fragmentList:ArrayList<Fragment> = ArrayList<Fragment>()

              val settings = appSettingRepository.getAppSettings()

              if(settings==null ){
                  fragmentList.add(SliderItemFragment())
                  val appSettings = AppSettings(is_fisrt_install = true)
                  saveAppSettings(appSettings)
              }


              fragmentList.add(SignUp())

              fragments.value = Resource.Success(data = fragmentList as List<Fragment>)
        }
    }

    fun saveAppSettings(appSetings:AppSettings){
        appSettingRepository.save(appSetings)
    }


    fun saveUser(user:User){
        val id = UUID.randomUUID().toString()
        user.user_id = id
        userRepository.save(user)
        userAccountRepository.save(
            UserAccount(
                user_id = id,
                refreshDate =" 30-${Date.getMonth()}-${Date.getYear()}")
        )

        userPayloadRepository.save(UserPayload(
            user_id = id,
            login_at = Date.currentDateTime()
        ))
    }

}