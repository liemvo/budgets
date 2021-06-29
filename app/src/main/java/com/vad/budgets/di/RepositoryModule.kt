package com.vad.budgets.di

import com.vad.budgets.data.repository.CategoryRepository
import com.vad.budgets.data.repository.CategoryRepositoryImpl
import com.vad.budgets.data.repository.TransactionDetailsRepository
import com.vad.budgets.data.repository.TransactionDetailsRepositoryImpl
import com.vad.budgets.data.repository.TransactionsRepository
import com.vad.budgets.data.repository.TransactionsRepositoryImpl
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
    abstract fun provideTransactionsRepository(transactionsRepository: TransactionsRepositoryImpl): TransactionsRepository

    @Binds
    @Singleton
    abstract fun provideTransactionDetailRepository(transactionDetailsRepository: TransactionDetailsRepositoryImpl): TransactionDetailsRepository
}
