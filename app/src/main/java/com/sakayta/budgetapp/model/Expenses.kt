package com.sakayta.budgetapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "expenses")
class Expenses(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    var transaction_id:Int = 0,

    @ColumnInfo(name = "account_owner_id")
    var acount_id:Int,

    @ColumnInfo(name = "amount")
    var amount:Double,

    @ColumnInfo(name = "date")
    var date:String,

    @ColumnInfo(name="remarks")
    var remarks:String

) : Parcelable{
    override fun toString(): String {
        return "Transaction(id=$transaction_id, acount_id=$acount_id, amount=$amount, date='$date', remarks='$remarks')"
    }
}