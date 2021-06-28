package com.vad.budgets.data.transaction

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

interface TransactionRepository {
    fun getAllTransaction(): LiveData<List<Transaction>>
}

@Singleton
class TransactionRepositoryImpl @Inject constructor(private val transactionDao: TransactionDao): TransactionRepository {
    override fun getAllTransaction(): LiveData<List<Transaction>> = transactionDao.getAll()
}
