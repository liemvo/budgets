package com.vad.budgets.data.transaction

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAll(): LiveData<List<Transaction>>
    
    @Query("SELECT * FROM transactions WHERE date >= :startTime and date < :endTime ORDER BY date DESC")
    fun getTransactionsByPeriod(startTime: Long, endTime: Long): LiveData<List<Transaction>>
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg transaction: Transaction)
    
    @Update
    fun update(transaction: Transaction): Int
    
    @Query("DELETE FROM transactions")
    fun clearTransactions()
    
    @Query("SELECT * FROM transactions WHERE id = :id")
    fun getTransactionsById(id: Long): Transaction
}
