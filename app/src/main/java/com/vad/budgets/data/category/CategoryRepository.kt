package com.vad.budgets.data.category

import androidx.lifecycle.LiveData
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

interface CategoryRepository {
    fun getAllCategory(): LiveData<List<Category>>
    suspend fun insertCategory(category: Category)
    suspend fun updateCategory(category: Category): Int
}

@Singleton
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
