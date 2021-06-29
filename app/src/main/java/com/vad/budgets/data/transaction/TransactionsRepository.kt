package com.vad.budgets.data.transaction

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

interface TransactionsRepository {
    fun getAllTransaction(): LiveData<List<Transaction>>
}

@Singleton
class TransactionsRepositoryImpl @Inject constructor(private val transactionDao: TransactionDao): TransactionsRepository {
    override fun getAllTransaction(): LiveData<List<Transaction>> = transactionDao.getAll()
}
