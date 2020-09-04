package com.sakayta.budgetapp.persistence

import androidx.room.*
import com.sakayta.budgetapp.model.Expenses

@Dao
interface ExpensesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tr: Expenses): Long

    @Query("DELETE from expenses where transaction_id =:id")
    suspend fun delete(id:Int)

}


















