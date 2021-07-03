package com.vad.budgets.ui.transaction.details

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vad.budgets.BudgetApplication
import com.vad.budgets.R
import com.vad.budgets.data.category.Category
import com.vad.budgets.data.repository.TransactionDetailsRepository
import com.vad.budgets.data.transaction.Currency
import com.vad.budgets.data.transaction.StaticData
import com.vad.budgets.data.transaction.Transaction
import com.vad.budgets.ui.common.input.DropdownModel
import com.vad.budgets.ui.common.input.TextFieldInput
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Singleton
class TransactionDetailsViewModel @Inject constructor(
    application: BudgetApplication,
    private val transactionDetailsRepository: TransactionDetailsRepository,
) : AndroidViewModel(application) {
    private var _transaction = MutableLiveData<Transaction?>(null)
    val transaction: LiveData<Transaction?> get() = _transaction
    val transactionName = TextFieldInput(application.getString(R.string.transaction_name))
    val accountName = TextFieldInput(application.getString(R.string.transaction_account))
    val transactionValue = TextFieldInput(application.getString(R.string.transaction_value))
    val transactionNote = TextFieldInput(application.getString(R.string.transaction_note))
    val currencyDropdownModel = DropdownModel<Currency>(application.getString(R.string.currency))
    val categoryDropDownModel = DropdownModel<Category>(application.getString(R.string.title_budgets))
    private lateinit var categories: LiveData<List<Category>>
    init {
        currencyDropdownModel.update(StaticData.currencies, Currency.NZD)
        viewModelScope.launch(Dispatchers.IO) {
            categories = transactionDetailsRepository.getCategories(true)
            withContext(Dispatchers.Main) {
                categories.observeForever {
                    categoryDropDownModel.updateUI(it, it.first())
                }
            }
        }
        transactionName.text.addSource(transaction) { transaction ->
            transaction?.let {

            }
        }
    }
}
