package com.vad.budgets.data.repository

import androidx.lifecycle.asFlow
import com.vad.budgets.data.WorkStatus
import com.vad.budgets.data.category.Category
import com.vad.budgets.data.category.CategoryDao
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface CategoryRepository {
    fun getAllCategory(): Flow<WorkStatus<List<Category>>>
    suspend fun insertCategory(category: Category)
    suspend fun updateCategory(category: Category): Int
    suspend fun getCategory(name: String): Category
}

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
) : CategoryRepository {
    override fun getAllCategory(): Flow<WorkStatus<List<Category>>> = categoryDao.getAllCategoriesLiveData().asFlow()
        .map { list -> WorkStatus.Success(list) }
        .onStart<WorkStatus<List<Category>>> { emit(WorkStatus.Loading(null)) }
        .catch { exception -> emit(WorkStatus.Error(exception = exception)) }
        .flowOn(Dispatchers.IO)


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
