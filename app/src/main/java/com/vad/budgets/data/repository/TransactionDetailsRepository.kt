package com.vad.budgets.data.repository

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
    suspend fun deleteTransaction(transaction: Transaction)
    fun getCategories(isEditing: Boolean): List<Category>
}

@Singleton
class TransactionDetailsRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao,
) : TransactionDetailsRepository {
    override suspend fun getTransaction(id: Long) = transactionDao.getTransactionsById(id)
    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.update(transaction)
    }

    override suspend fun addTransaction(transaction: Transaction) {
        transactionDao.insert(transaction)
    }

    override fun getCategories(isEditing: Boolean): List<Category> = if (isEditing) {
        categoryDao.getAllCategories()
    } else {
        categoryDao.getAllActiveCategories()
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.delete(transaction)
    }
}
