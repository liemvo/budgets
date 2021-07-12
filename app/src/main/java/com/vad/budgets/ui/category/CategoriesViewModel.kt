package com.vad.budgets.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.vad.budgets.data.repository.CategoryRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.collect


@Singleton
class CategoriesViewModel @Inject constructor(
    categoryRepository: CategoryRepository
) : ViewModel() {
    val categories = liveData(context = viewModelScope.coroutineContext) {
        categoryRepository.getAllCategory().collect { state ->
            emit(state)
        }
    }
}
