package com.vad.budgets.data.repository

import androidx.lifecycle.LiveData
import com.vad.budgets.data.category.Category
import com.vad.budgets.data.category.CategoryDao
import javax.inject.Inject
import javax.inject.Singleton

interface CategoryRepository {
    fun getAllCategory(): LiveData<List<Category>>
    suspend fun insertCategory(category: Category)
    suspend fun updateCategory(category: Category): Int
    suspend fun getCategory(name: String): Category
}

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override fun getAllCategory(): LiveData<List<Category>> = categoryDao.getAllCategoriesLiveData()
    
    override suspend fun insertCategory(category: Category) {
        categoryDao.insert(category)
    }
    
    override suspend fun updateCategory(category: Category): Int {
        return categoryDao.update(category)
    }
    
    override suspend fun getCategory(name: String): Category {
        return categoryDao.getCategoryByName(name)
    }
}
