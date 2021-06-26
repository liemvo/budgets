package com.vad.budgets.ui.category

import androidx.lifecycle.ViewModel
import com.vad.budgets.data.category.CategoryRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CategoriesViewModel @Inject constructor(categoryRepository: CategoryRepository) :
    ViewModel() {
    val categories = categoryRepository.getAllCategory()
}
