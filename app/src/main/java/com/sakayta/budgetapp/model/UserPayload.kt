package com.sakayta.budgetapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_payload")
class UserPayload(
  @PrimaryKey
  @ColumnInfo(name = "id")
  var id:Int=0,
  var user_id:String = "",
  var login_at:String = ""
) : Parcelable {

}

