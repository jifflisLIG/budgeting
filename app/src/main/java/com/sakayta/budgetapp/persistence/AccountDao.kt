package com.sakayta.budgetapp.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sakayta.budgetapp.model.Account
import com.sakayta.budgetapp.model.AcountWithTransaction
import com.sakayta.budgetapp.util.Constants

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: Account): Long

    @Update
    suspend fun update(account: Account):Int

    @Query("DELETE from account where account_id =:id")
    suspend fun delete(id:Int)

    @Query("SELECT * FROM account order by account_id LIMIT(:page * :pageSize)")
    fun getAllAcounts(page: Int,pageSize: Int = Constants.PAGINATION_PAGE_SIZE):List<Account>


    @Query("SELECT * FROM account where account_id LIKE '%' || :query|| '%'")
    fun getAccount(query:String):LiveData<Account>


    @Query("SELECT * FROM account" +
            " WHERE account_name LIKE '%' || :query || '%' " +
            " ORDER BY account_id DESC LIMIT (:page * :pageSize)"
    )
    suspend fun searchAccounts(
        query: String?,
        page: Int,
        pageSize: Int = Constants.PAGINATION_PAGE_SIZE
    ):List<Account>


    @Transaction
    @Query("SELECT * FROM Account")
    suspend fun getAcountAndExpense(): List<AcountWithTransaction>

}


















