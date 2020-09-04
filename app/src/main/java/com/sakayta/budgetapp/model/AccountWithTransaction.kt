package com.sakayta.budgetapp.model

import androidx.room.Embedded
import androidx.room.Relation

data class AcountWithTransaction(

    @Embedded val account: Account,
    @Relation(
        parentColumn = "account_id",
        entityColumn = "account_owner_id"
    )
    val transaction: List<Expenses>
){
    override fun toString(): String {
        return "AcountAndTransaction(account=$account, transaction=$transaction)"
    }

}