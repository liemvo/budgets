package com.vad.budgets.data.repository

import androidx.lifecycle.LiveData
import com.vad.budgets.data.category.Category
import com.vad.budgets.data.category.CategoryDao
import com.vad.budgets.data.transaction.Transaction
import com.vad.budgets.data.transaction.TransactionDao
import javax.inject.Inject
import javax.inject.Singleton

interface TransactionDetailsRepository {
    suspend fun getTransaction(id: Long): Transaction
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun addTransaction(transaction: Transaction)
    suspend fun getCategories(isEditing: Boolean): LiveData<List<Category>>
}

@Singleton
class TransactionDetailsRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao
) : TransactionDetailsRepository {
    override suspend fun getTransaction(id: Long) = transactionDao.getTransactionsById(id)
    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.update(transaction)
    }
    override suspend fun addTransaction(transaction: Transaction) {
        transactionDao.insert(transaction)
    }

    override suspend fun getCategories(isEditing: Boolean): LiveData<List<Category>> = if(isEditing) {
        categoryDao.getAll()
    } else {
        categoryDao.getAllActive()
    }
}
