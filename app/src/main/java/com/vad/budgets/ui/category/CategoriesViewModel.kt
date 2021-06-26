package com.vad.budgets.ui.category

import androidx.lifecycle.ViewModel
import com.vad.budgets.data.category.CategoryRepository
import com.vad.budgets.di.scope.CategoryScope
import javax.inject.Inject


@CategoryScope
class CategoriesViewModel @Inject constructor(categoryRepository: CategoryRepository): ViewModel() {
    val categories = categoryRepository.getAllCategory()
}
