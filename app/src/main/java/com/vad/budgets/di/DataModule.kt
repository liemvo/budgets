package com.vad.budgets.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.vad.budgets.data.BudgetDatabase
import com.vad.budgets.data.budget.BudgetDao
import com.vad.budgets.data.category.CategoryDao
import com.vad.budgets.data.transaction.TransactionDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    companion object {
        private const val databaseName = "budget_database"
    }
    
    @Provides
    @Singleton
    fun provideItemDatabase(context: Context): BudgetDatabase = Room.databaseBuilder(
        context.applicationContext,
        BudgetDatabase::class.java,
        databaseName
    ).build()
    
    @Provides
    @Singleton
    fun provideCategoryDao(budgetDatabase: BudgetDatabase): CategoryDao =
        budgetDatabase.categoryDao()
    
    @Provides
    @Singleton
    fun provideBudgetDao(budgetDatabase: BudgetDatabase): BudgetDao = budgetDatabase.budgetDao()
    
    @Provides
    @Singleton
    fun provideTransactionDao(budgetDatabase: BudgetDatabase): TransactionDao =
        budgetDatabase.transactionDao()
    
    @Provides
    @Singleton
    fun provideGson() = Gson()
}
