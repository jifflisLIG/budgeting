package com.sakayta.budgetapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Date


class Date {

    companion object {
        fun currentDateTime(): String {
            val date = Calendar.getInstance().time
            val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
            val formatedDate = formatter.format(date)
            return formatedDate
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun currentDate(): LocalDate {
            val date = Calendar.getInstance().time
            val formatter = SimpleDateFormat("yyyy-MM-dd") //or use getDateInstance()
            val formatedDate = formatter.format(date)
            return stringToDate(formatedDate)
        }


        fun getMonth(): String {
            return "05"
        }

        fun getDay(): String {
            return ""
        }

        fun getYear(): String {
            return "2020"
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun stringToDate(date: String): LocalDate {
            return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        }

    }


}

