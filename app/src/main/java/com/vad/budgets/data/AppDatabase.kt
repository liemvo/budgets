
package com.vad.budgets.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Category::class, Budget::class, Transaction::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun budgetDao(): BudgetDao
    abstract fun transactionDao(): TransactionDao
}
