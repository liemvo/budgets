package com.vad.budgets.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vad.budgets.data.category.Category
import com.vad.budgets.data.category.CategoryDao
import com.vad.budgets.data.transaction.Currency
import com.vad.budgets.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CategoryEntityTests {
    private lateinit var categoryDao: CategoryDao
    private lateinit var db: AppDatabase
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        )
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()
        categoryDao = db.categoryDao()
    }
    
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
    
    @Test
    @Throws(IOException::class)
    fun insertNewCategory() {
        val category =
            Category(name = "Rent", defaultAmount = 450f, isActive = true, currency = currency)
        runBlockingTest {
            categoryDao.insert(category)
        }
        
        val categories = categoryDao.getAll().getOrAwaitValue()
        
        val savedCategory = categories.first()
        
        assertTrue(categories.size == 1)
        assertEquals(category.name, savedCategory.name)
        assertEquals(category.defaultAmount, savedCategory.defaultAmount)
    }
    
    @Test
    @Throws(IOException::class)
    fun updateCategory() {
        val category =
            Category(name = "Rent", defaultAmount = 450f, isActive = true, currency = currency)
        runBlockingTest {
            categoryDao.insert(category)
        }
        
        val savedCategory = categoryDao.getAll().getOrAwaitValue().first()
        val newCategory = savedCategory.copy(isActive = false)
        categoryDao.update(newCategory)
        val updatedCategory = categoryDao.getCategoryByName(savedCategory.name)
        
        assertEquals(newCategory, updatedCategory)
    }
    
    @Test
    @Throws(IOException::class)
    fun clearCategories() {
        val category =
            Category(name = "Rent", defaultAmount = 450f, isActive = true, currency = currency)
        runBlockingTest {
            categoryDao.insert(category)
        }
        
        val categories = categoryDao.getAll().getOrAwaitValue()
        
        assertTrue(categories.size == 1)
        
        runBlockingTest {
            categoryDao.clearCategories()
        }
        assertTrue(categoryDao.getAll().getOrAwaitValue().isEmpty())
    }
    
    @Test
    @Throws(IOException::class)
    fun insertDuplicateCategoryName_ThenIgnore() {
        val category =
            Category(name = "Rent", defaultAmount = 450f, isActive = true, currency = currency)
        val category2 =
            Category(name = "Rent", defaultAmount = 50f, isActive = true, currency = currency)
        runBlockingTest {
            val result = categoryDao.insert(category)
            val result2 = categoryDao.insert(category2)
            
            assertEquals(1, result)
            assertEquals(-1, result2)
        }
    
        val categories = categoryDao.getAll().getOrAwaitValue()
    
        assertTrue(categories.size == 1)
        val savedCategory = categories.first()
        
        assertEquals(category.defaultAmount, savedCategory.defaultAmount)
        assertEquals(category.name, savedCategory.name)
    }
    
    @Test
    @Throws(IOException::class)
    fun getAllActiveCategory() {
        val category =
            Category(name = "Rent", defaultAmount = 450f, isActive = true, currency = currency)
        val category2 =
            Category(name = "Investment", defaultAmount = 50f, isActive = true, currency = currency)
        val category3 =
            Category(name = "Work", defaultAmount = 50f, isActive = false, currency = currency)
        runBlockingTest {
            categoryDao.insertAll(category, category2, category3)
        }
        
        assertTrue(categoryDao.getAll().getOrAwaitValue().size == 3)
        assertTrue(categoryDao.getAllActive().getOrAwaitValue().size == 2)
    }
    
    companion object {
        private val currency = Currency.NZD.value
    }
}
