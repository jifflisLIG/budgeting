package com.sakayta.budgetapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "account_ledger")
class AccountLedger(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int = 0,

    @ColumnInfo(name = "account_id")
    var account_id:Int = 0,

    @ColumnInfo(name = "beginning_balance")
    var beginning_balance:Double,

    @ColumnInfo(name="ending_balance")
    var ending_balance:Double = 0.0,

    @ColumnInfo(name = "amount_expense")
    var amount_expense:Double = 0.0,

    @ColumnInfo(name = "amount_budget")
    var amount_budget:Double = 0.0,

    @ColumnInfo(name = "amount_adjusted")
    var amount_adjusted:Double = 0.0,

    @ColumnInfo(name = "status")
    var status:Int = 0,

    @ColumnInfo(name = "month")
    var month:Int,

    @ColumnInfo(name = "year")
    var year:Int


) : Parcelable{

}