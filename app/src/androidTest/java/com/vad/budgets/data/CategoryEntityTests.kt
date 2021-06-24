package com.vad.budgets.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CategoryEntityTests {
    private lateinit var categoryDao: CategoryDao
    private lateinit var db: AppDatabase
    
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
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
        val category = Category(name = "Rent", defaultAmount = 450f, isActive = true)
        categoryDao.insert(category)
        
        val categories = categoryDao.getAll()
        
        val savedCategory = categories.first()
        
        assertTrue(categories.size == 1)
        assertEquals(category.name, savedCategory.name)
        assertEquals(category.defaultAmount, savedCategory.defaultAmount)
    }
    
    @Test
    @Throws(IOException::class)
    fun updateCategory() {
        val category = Category(name = "Rent", defaultAmount = 450f, isActive = true)
        categoryDao.insert(category)
        
        val savedCategory = categoryDao.getAll().first()
        val newCategory = savedCategory.copy(isActive = false)
        categoryDao.update(newCategory)
        val updatedCategory = categoryDao.getCategoryByName(savedCategory.name)
        
        assertEquals(newCategory, updatedCategory)
    }
    
    @Test
    @Throws(IOException::class)
    fun clearCategories() {
        val category = Category(name = "Rent", defaultAmount = 450f, isActive = true)
        categoryDao.insert(category)
        
        val categories = categoryDao.getAll()
        
        assertTrue(categories.size == 1)
        
        categoryDao.clearCategories()
        assertTrue(categoryDao.getAll().isEmpty())
    }
    
    @Test
    @Throws(IOException::class)
    fun insertDuplicateCategoryName_ThenIgnore() {
        val category = Category(name = "Rent", defaultAmount = 450f, isActive = true)
        val category2 = Category(name = "Rent", defaultAmount = 50f, isActive = true)
        val result = categoryDao.insert(category)
        val result2 = categoryDao.insert(category2)
        
        val categories = categoryDao.getAll()
        
        assertTrue(categories.size == 1)
        val savedCategory = categories.first()
        
        assertEquals(1, result)
        assertEquals(-1, result2)
        assertEquals(category.defaultAmount, savedCategory.defaultAmount)
        assertEquals(category.name, savedCategory.name)
    }
}
