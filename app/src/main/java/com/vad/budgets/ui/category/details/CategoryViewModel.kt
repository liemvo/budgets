package com.vad.budgets.ui.category.details

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vad.budgets.BudgetApplication
import com.vad.budgets.R
import com.vad.budgets.data.category.Category
import com.vad.budgets.data.category.CategoryRepository
import com.vad.budgets.data.toCurrency
import com.vad.budgets.data.transaction.Currency
import com.vad.budgets.data.transaction.StaticData
import com.vad.budgets.ui.input.DropdownModel
import com.vad.budgets.ui.input.TextFieldInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryViewModel @Inject constructor(private val app: BudgetApplication, private val categoryRepository: CategoryRepository) : AndroidViewModel(app) {
    private val categoryLiveData = MutableLiveData<Category>()
    val nameInput = TextFieldInput(app.getString(R.string.category_name))
    val defaultValueInput = TextFieldInput(app.getString(R.string.category_default_value))
    val currencyDropdownModel = DropdownModel<Currency>(app.getString(R.string.currency))
    init {
        currencyDropdownModel.update(StaticData.currencies, Currency.NZD)
    }
    suspend fun loadCategory(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val category = categoryRepository.getCategory(name)
            categoryLiveData.postValue(category)
            category.currency.toCurrency()?.let {
                currencyDropdownModel.update(StaticData.currencies, it)
            }
            
        }
    }
}
