package com.vad.budgets.data.budget

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budgets ORDER BY startTime DESC")
    fun getAll(): List<Budget>
    
    @Query("SELECT * FROM budgets WHERE startTime = :startTime AND endTime = :endTime ORDER BY name DESC")
    fun getAllBudgetsInPeriod(startTime: Long, endTime: Long): List<Budget>
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(budget: Budget): Long
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBudgets(vararg budget: Budget)
    
    @Update
    fun update(budget: Budget): Int
    
    @Query("DELETE FROM budgets")
    fun clearBudgets()
    
    @Query("SELECT * FROM budgets WHERE startTime = :startTime and endTime = :endTime and name = :name")
    fun getBudget(name: String, startTime: Long, endTime: Long): Budget
}
