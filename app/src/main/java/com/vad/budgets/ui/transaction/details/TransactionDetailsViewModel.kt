package com.vad.budgets.ui.transaction.details

import androidx.lifecycle.AndroidViewModel
import com.vad.budgets.BudgetApplication
import com.vad.budgets.data.repository.TransactionDetailsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionDetailsViewModel @Inject constructor(
    private val application: BudgetApplication,
    private val transactionDetailsRepository: TransactionDetailsRepository,
) : AndroidViewModel(application) {

}