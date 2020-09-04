package com.sakayta.budgetapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "user")
class User(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int=0,

    @ColumnInfo(name = "user_id")
    var user_id:String ="",

    @ColumnInfo(name = "email")
    var email:String,

    @ColumnInfo(name="username")
    var username:String,

    @ColumnInfo(name="password")
    var password:String = "",

    @ColumnInfo(name = "pic_path")
    var pic_path:String = ""

) : Parcelable {


}