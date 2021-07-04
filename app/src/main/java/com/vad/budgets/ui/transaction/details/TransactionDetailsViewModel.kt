package com.vad.budgets.ui.transaction.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vad.budgets.R
import com.vad.budgets.data.WorkStatus
import com.vad.budgets.data.category.Category
import com.vad.budgets.data.repository.TransactionDetailsRepository
import com.vad.budgets.data.toCurrency
import com.vad.budgets.data.toTransactionType
import com.vad.budgets.data.transaction.Currency
import com.vad.budgets.data.transaction.StaticData
import com.vad.budgets.data.transaction.Transaction
import com.vad.budgets.data.transaction.TransactionType
import com.vad.budgets.ui.common.input.DropdownModel
import com.vad.budgets.ui.common.input.TextFieldInput
import com.vad.budgets.ui.transaction.amountInString
import com.vad.budgets.ui.transaction.nonNull
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Singleton
class TransactionDetailsViewModel @Inject constructor(
    application: Application,
    private val transactionDetailsRepository: TransactionDetailsRepository,
) : AndroidViewModel(application) {
    private var _transaction = MutableLiveData<Transaction?>(null)
    val transaction: LiveData<Transaction?> get() = _transaction
    private var _source = MutableLiveData<WorkStatus<Unit>>()
    val status: LiveData<WorkStatus<Unit>> get() = _source
    val transactionName = TextFieldInput(application.getString(R.string.transaction_name))
    val accountName = TextFieldInput(application.getString(R.string.transaction_account))
    val transactionValue = TextFieldInput(application.getString(R.string.transaction_value))
    val transactionNote = TextFieldInput(application.getString(R.string.transaction_note))
    val currencyDropdownModel = DropdownModel<Currency>(application.getString(R.string.currency))
    val categoryDropDownModel = DropdownModel<Category>(
        application.getString(R.string.title_budgets)
    )
    val transactionTypeModel = DropdownModel<TransactionType>(
        application.getString(R.string.transaction_type)
    )

    init {
        currencyDropdownModel.update(StaticData.currencies, Currency.NZD)
        transactionTypeModel.update(StaticData.transactionTypes, TransactionType.EXPENSE)
        updateCategorySpinner()
        transactionName.text.addSource(transaction) { transaction ->
            transaction?.let {
                transactionName.update(it.categoryName)
                accountName.update(it.accountName.nonNull())
                transactionValue.update(it.amountInString())
                transactionNote.update(it.note.nonNull())
                currencyDropdownModel.selectedOption.postValue(it.currency.toCurrency())
                updateCategorySpinner(it.categoryName)
                transactionTypeModel.selectedOption.postValue(it.transactionType.toTransactionType())
            }
        }
    }

    private fun updateCategorySpinner(category: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val categories = transactionDetailsRepository.getCategories(category != null)
            if (!categories.isNullOrEmpty()) {
                categoryDropDownModel.update(categories,
                    categories.firstOrNull { it.name == category } ?: categories.first())
            }
        }
    }

    fun getExistTransaction(id: Long) {
        if (id == DEFAULT_NEW_ID) return
        _source.value = WorkStatus.Loading(null)
        viewModelScope.launch(Dispatchers.IO) {
            _transaction.postValue(transactionDetailsRepository.getTransaction(id))
            _source.postValue(WorkStatus.Success(Unit))
        }
    }

    fun deleteTransaction() {
        _transaction.value?.let {
            _source.value = WorkStatus.Loading(null)
            viewModelScope.launch(Dispatchers.IO) {
                transactionDetailsRepository.deleteTransaction(it)
                _source.postValue(WorkStatus.Finish)
            }
        }
    }

    fun saveTransaction() {
        val isAdding = _transaction.value == null
        if (isAdding) {

        }
    }

    fun resetValues() {
        transactionName.update("")
        accountName.update("")
        transactionValue.update("")
        transactionNote.update("")
        currencyDropdownModel.selectedOption.postValue(Currency.NZD)
        updateCategorySpinner()
        transactionTypeModel.selectedOption.postValue(TransactionType.EXPENSE)
    }

    companion object {
        const val DEFAULT_NEW_ID = -1L
    }
}
