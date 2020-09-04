package com.sakayta.budgetapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "account")
class Account(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "account_id")
    var account_id:Int = 0,

    @ColumnInfo(name = "account_name")
    var acount_name:String?,

    @ColumnInfo(name = "amount_budget")
    var amount_budget:Double,

    @ColumnInfo(name="amount_expense")
    var amount_expense:Double = 0.0,

    @ColumnInfo(name = "amount_balance")
    var amount_balance:Double = 0.0,

    @ColumnInfo(name = "created_at")
    var created_at:String = ""
) : Parcelable{
    override fun toString(): String {
        return "Account(id=$account_id, acount_name=$acount_name, amount_budget=$amount_budget, amount_expense=$amount_expense, amount_balance=$amount_balance, created_at='$created_at')"
    }
}