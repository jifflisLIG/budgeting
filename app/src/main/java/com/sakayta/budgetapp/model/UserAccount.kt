package com.sakayta.budgetapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_account")
class UserAccount(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id:Int=0,
    var refreshDate:String ="",
    var user_id:String=""
) : Parcelable {

}