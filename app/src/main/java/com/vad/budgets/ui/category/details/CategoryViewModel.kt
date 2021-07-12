package com.vad.budgets.ui.category.details

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.vad.budgets.BudgetApplication
import com.vad.budgets.R
import com.vad.budgets.data.WorkStatus
import com.vad.budgets.data.category.Category
import com.vad.budgets.data.repository.CategoryRepository
import com.vad.budgets.data.toCurrency
import com.vad.budgets.data.transaction.Currency
import com.vad.budgets.data.transaction.StaticData
import com.vad.budgets.ui.common.input.DropdownModel
import com.vad.budgets.ui.common.input.TextFieldInput
import com.vad.budgets.util.Utility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.collect

@Singleton
class CategoryViewModel @Inject constructor(
    private val app: BudgetApplication,
    private val categoryRepository: CategoryRepository
) : AndroidViewModel(app) {
    val editCategory: LiveData<Category?> get() = category
    private val category = MutableLiveData<Category?>()
    val nameInput = TextFieldInput(app.getString(R.string.category_name))
    val defaultValueInput = TextFieldInput(app.getString(R.string.category_default_value))
    val currencyDropdownModel = DropdownModel<Currency>(app.getString(R.string.currency))
    val isActive = MediatorLiveData<Boolean>()

    private val _workStatus = MutableLiveData<WorkStatus<Unit>>(WorkStatus.Initial)
    val status: LiveData<WorkStatus<Unit>> get() = _workStatus
    
    private val allCategory = liveData(context = viewModelScope.coroutineContext) {
        categoryRepository.getAllCategory().collect {
            emit(it)
        }
    }
    
    init {
        currencyDropdownModel.update(StaticData.currencies, Currency.NZD)
        
        nameInput.text.addSource(category) { category ->
            category?.let {
                nameInput.text.postValue(it.name)
                defaultValueInput.text.postValue(Utility.numberFormat.format(it.defaultAmount))
                category.currency.toCurrency().let {
                    currencyDropdownModel.update(StaticData.currencies, it)
                }
                isActive.postValue(it.isActive)
            }
        }
    }
    
    fun loadCategory(name: String) {
        _workStatus.value = WorkStatus.Loading(null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                category.postValue(categoryRepository.getCategory(name))
            } catch (exception: Exception) {
                Timber.e(exception)
            }
            _workStatus.postValue(WorkStatus.Success(Unit))
        }
    }
    
    fun clearSaveOrUpdateState() {
        _workStatus.value = WorkStatus.Loading(null)
    }
    
    
    fun saveOrUpdateCategory() {
        val name = nameInput.text.value
        val category = category.value
        val categories = allCategory.value?.isNullableData
        if (name.isNullOrEmpty()) {
            nameInput.showError(app.getString(R.string.category_name_error))
            return
        }
        
        val isExistCategoryName =
            categories?.firstOrNull { it.name.equals(name, ignoreCase = true) } != null
        if (category == null && isExistCategoryName) {
            nameInput.showError(app.getString(R.string.category_name_duplicated_error))
            return
        }

        _workStatus.value = WorkStatus.Loading(null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (category == null) {
                    categoryRepository.insertCategory(
                        Category(
                            name,
                            defaultValueInput.text.value?.toFloatOrNull() ?: 0f,
                            currencyDropdownModel.selectedOption.value?.value ?: Currency.NZD.value,
                            isActive.value ?: true
                        )
                    )
                } else {
                    categoryRepository.updateCategory(
                        Category(
                            name,
                            defaultValueInput.text.value?.toFloatOrNull() ?: 0f,
                            currencyDropdownModel.selectedOption.value?.value ?: Currency.NZD.value,
                            isActive.value ?: true
                        )
                    )
                }
            } catch (exception: Exception) {
                Timber.e(exception)
            }
            _workStatus.postValue(WorkStatus.Finish)
        }
    }
    
    fun resetFields() {
        category.postValue(null)
        nameInput.update("")
        defaultValueInput.update("")
        currencyDropdownModel.selectedOption.postValue(Currency.NZD)
        isActive.postValue(true)
        _workStatus.postValue(WorkStatus.Initial)
    }
}
