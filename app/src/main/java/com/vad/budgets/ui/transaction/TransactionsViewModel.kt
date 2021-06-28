package com.vad.budgets.ui.transaction

import androidx.lifecycle.ViewModel
import com.vad.budgets.data.transaction.TransactionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionsViewModel @Inject constructor(transactionRepository: TransactionRepository) : ViewModel() {
    val transactions = transactionRepository.getAllTransaction()
}
