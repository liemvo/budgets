package com.vad.budgets.data.category

import androidx.lifecycle.LiveData
import com.vad.budgets.di.scope.CategoryScope
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

interface CategoryRepository {
    fun getAllCategory(): LiveData<List<Category>>
    suspend fun insertCategory(category: Category)
    suspend fun updateCategory(category: Category): Int
}

@CategoryScope
class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override fun getAllCategory(): LiveData<List<Category>> = categoryDao.getAll()
    
    override suspend fun insertCategory(category: Category) {
        coroutineScope {
            categoryDao.insert(category)
        }
    }
    
    override suspend fun updateCategory(category: Category) = coroutineScope {
        categoryDao.update(category)
    }
}
