package com.sakayta.budgetapp.util

import java.text.SimpleDateFormat
import java.util.*


class Date {

    companion object{
        fun currentDateTime():String{
            val date = Calendar.getInstance().time
            val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
            val formatedDate = formatter.format(date)
            println("the date $formatedDate")
            return  formatedDate
        }


        fun getMonth():String{
            return "05"
        }

        fun  getDay():String{
            return ""
        }

        fun getYear():String{
            return "2020"
        }

    }



}

