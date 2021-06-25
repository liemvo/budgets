
package com.vad.budgets.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vad.budgets.data.budget.Budget
import com.vad.budgets.data.budget.BudgetDao
import com.vad.budgets.data.category.Category
import com.vad.budgets.data.category.CategoryDao
import com.vad.budgets.data.transaction.Transaction
import com.vad.budgets.data.transaction.TransactionDao

@Database(entities = [Category::class, Budget::class, Transaction::class], version = 1, exportSchema = false)
abstract class BudgetDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun budgetDao(): BudgetDao
    abstract fun transactionDao(): TransactionDao
}
