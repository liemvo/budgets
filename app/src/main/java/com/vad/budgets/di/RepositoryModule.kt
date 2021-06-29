package com.vad.budgets.di

import com.vad.budgets.data.category.CategoryRepository
import com.vad.budgets.data.category.CategoryRepositoryImpl
import com.vad.budgets.data.transaction.TransactionsRepository
import com.vad.budgets.data.transaction.TransactionsRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository
    
    @Binds
    @Singleton
    abstract fun provideTransactionRepository(transactionRepository: TransactionsRepositoryImpl): TransactionsRepository
}
