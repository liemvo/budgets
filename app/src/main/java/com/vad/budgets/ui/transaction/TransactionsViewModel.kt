package com.vad.budgets.ui.transaction

import androidx.lifecycle.ViewModel
import com.vad.budgets.data.transaction.TransactionsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionsViewModel @Inject constructor(transactionsRepository: TransactionsRepository) : ViewModel() {
    val transactions = transactionsRepository.getAllTransaction()
}
