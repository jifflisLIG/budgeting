package com.sakayta.budgetapp.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sakayta.budgetapp.model.AccountLedger

@Dao
interface AccountLedgerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: AccountLedger): Long

    @Update
    suspend fun update(account: AccountLedger): Int

    @Query("DELETE from account_ledger where account_id =:id")
    suspend fun delete(id: Int)


    @Query("SELECT * FROM account_ledger where account_id LIKE '%' || :query|| '%'")
    fun getAccount(query: String): LiveData<AccountLedger>


}


















