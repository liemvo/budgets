package com.vad.budgets.ui.transaction.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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
import com.vad.budgets.ui.transaction.SelectedTime
import com.vad.budgets.ui.transaction.amountInString
import com.vad.budgets.ui.transaction.nonNull
import com.vad.budgets.util.Utility
import com.vad.budgets.util.Utility.format
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@Singleton
class TransactionDetailsViewModel @Inject constructor(
    private val _application: Application,
    private val transactionDetailsRepository: TransactionDetailsRepository,
    private val eventBus: EventBus,
) : AndroidViewModel(_application) {
    private var _transaction = MutableLiveData<Transaction?>(null)
    val transaction: LiveData<Transaction?> get() = _transaction
    private var workStatus = MutableLiveData<WorkStatus<Unit>>()
    val status: LiveData<WorkStatus<Unit>> get() = workStatus
    val transactionName = TextFieldInput(_application.getString(R.string.transaction_name))
    val accountName = TextFieldInput(_application.getString(R.string.transaction_account))
    val transactionValue = TextFieldInput(_application.getString(R.string.transaction_value))
    val transactionNote = TextFieldInput(_application.getString(R.string.transaction_note))
    val currencyDropdownModel = DropdownModel<Currency>(_application.getString(R.string.currency))
    val categoryDropDownModel = DropdownModel<Category>(
        _application.getString(R.string.title_budgets)
    )
    val transactionTypeModel = DropdownModel<TransactionType>(
        _application.getString(R.string.transaction_type)
    )

    private var _timeInMilliSeconds = MutableLiveData<Long?>()
    val timeInMilliSeconds: LiveData<Long?> get() = _timeInMilliSeconds

    val timeInString: LiveData<String?> = Transformations.map(_timeInMilliSeconds) { time ->
        time?.let {
            Utility.dateFrom(it).format()
        }
    }

    init {
        currencyDropdownModel.update(StaticData.currencies, Currency.NZD)
        transactionTypeModel.update(StaticData.transactionTypes, TransactionType.EXPENSE)
        updateCategorySpinner()
        eventBus.register(this)
        _timeInMilliSeconds.postValue(System.currentTimeMillis())
        transactionName.text.addSource(transaction) { transaction ->
            transaction?.let {
                transactionName.update(it.categoryName)
                accountName.update(it.accountName.nonNull())
                transactionValue.update(it.amountInString())
                transactionNote.update(it.note.nonNull())
                currencyDropdownModel.selectedOption.postValue(it.currency.toCurrency())
                updateCategorySpinner(it.categoryName)
                transactionTypeModel.selectedOption.postValue(it.transactionType.toTransactionType())
                _timeInMilliSeconds.postValue(it.date)
            }
        }
    }

    private fun updateCategorySpinner(category: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val categories = transactionDetailsRepository.getCategories(category != null)
            if (!categories.isNullOrEmpty()) {
                categoryDropDownModel.update(
                    categories,
                    categories.firstOrNull { it.name == category } ?: categories.first()
                )
            }
        }
    }

    fun getExistTransaction(id: Long) {
        if (id == DEFAULT_NEW_ID) return
        workStatus.value = WorkStatus.Loading(null)
        viewModelScope.launch(Dispatchers.IO) {
            _transaction.postValue(transactionDetailsRepository.getTransaction(id))
            workStatus.postValue(WorkStatus.Success(Unit))
        }
    }

    fun deleteTransaction() {
        _transaction.value?.let {
            workStatus.value = WorkStatus.Loading(null)
            viewModelScope.launch(Dispatchers.IO) {
                transactionDetailsRepository.deleteTransaction(it)
                workStatus.postValue(WorkStatus.Finish)
            }
        }
    }

    fun saveTransaction() {
        val transactionName = transactionName.text.value ?: run {
            showTransactionNameError()
            return
        }
        if (transactionName.length < MIN_NAME_LENGTH) {
            showTransactionNameError()
            return
        }

        val value = transactionValue.text.value?.toFloatOrNull() ?: run {
            showTransactionValueError()
            return
        }
        if (value < 0) {
            showTransactionValueError()
            return
        }

        val categoryName = categoryDropDownModel.selectedOption.value?.name ?: return
        val currency = currencyDropdownModel.selectedOption.value?.value ?: return
        val transactionType = transactionTypeModel.selectedOption.value?.type ?: return

        val time = timeInMilliSeconds.value ?: return

        workStatus.postValue(WorkStatus.Loading(Unit))
        viewModelScope.launch(Dispatchers.IO) {
            val newTransaction = transaction.value?.copy(categoryName = categoryName,
                title = transactionName,
                accountName = accountName.text.value,
                amount = value,
                currency = currency,
                transactionType = transactionType,
                note = transactionNote.text.value,
                date = time
            ) ?: Transaction(
                categoryName = categoryName,
                title = transactionName,
                accountName = accountName.text.value,
                amount = value,
                currency = currency,
                transactionType = transactionType,
                note = transactionNote.text.value,
                date = time
            )

            val deferred = async {
                if (transaction.value == null) {
                    transactionDetailsRepository.addTransaction(newTransaction)
                } else {
                    transactionDetailsRepository.updateTransaction(newTransaction)
                }
            }
            deferred.await()
            workStatus.postValue(WorkStatus.Finish)
        }
    }

    private fun showTransactionNameError() {
        transactionName.showError(_application.getString(R.string.transaction_name_error))
    }

    private fun showTransactionValueError() {
        transactionName.showError(_application.getString(R.string.transaction_value_error))
    }

    fun resetValues() {
        transactionName.update("")
        accountName.update("")
        transactionValue.update("")
        transactionNote.update("")
        currencyDropdownModel.selectedOption.postValue(Currency.NZD)
        updateCategorySpinner()
        transactionTypeModel.selectedOption.postValue(TransactionType.EXPENSE)
        _timeInMilliSeconds.postValue(null)
        _transaction.postValue(null)
        workStatus.postValue(WorkStatus.Initial)
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onTimeUpdated(selectedTime: SelectedTime) {
        _timeInMilliSeconds.postValue(selectedTime.time)
    }

    override fun onCleared() {
        eventBus.unregister(this)
        super.onCleared()
    }

    companion object {
        const val DEFAULT_NEW_ID = -1L
        const val MIN_NAME_LENGTH = 3
    }
}
