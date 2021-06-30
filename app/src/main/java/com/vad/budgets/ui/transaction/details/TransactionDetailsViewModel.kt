package com.vad.budgets.ui.transaction.details

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vad.budgets.BudgetApplication
import com.vad.budgets.R
import com.vad.budgets.data.repository.TransactionDetailsRepository
import com.vad.budgets.data.transaction.Transaction
import com.vad.budgets.ui.common.input.TextFieldInput
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionDetailsViewModel @Inject constructor(
    private val application: BudgetApplication,
    private val transactionDetailsRepository: TransactionDetailsRepository,
) : AndroidViewModel(application) {
    private var _transaction = MutableLiveData<Transaction>()
    val transaction: LiveData<Transaction> get() = _transaction
    val transactionName = TextFieldInput(application.getString(R.string.transaction_name))
}
