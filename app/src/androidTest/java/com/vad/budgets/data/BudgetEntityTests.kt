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
class BudgetEntityTests {
    private lateinit var budgetDao: BudgetDao
    private lateinit var db: AppDatabase
    
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        budgetDao = db.budgetDao()
    }
    
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
    
    @Test
    @Throws(IOException::class)
    fun insertNewBudget() {
        val category = Category(categoryId = 40L, name = "Rent", defaultAmount = 450f, isActive = true)
        val budget = Budget(
            amount = 500f,
            startTime = 40L,
            endTime = 500L,
            category = category
        )
        val result = budgetDao.insert(budget)
        val budgets = budgetDao.getAll()
        val savedBudget = budgets.first()
    
        assertEquals(1, result)
        assertTrue(budgets.size == 1)
        assertEquals(category, savedBudget.category)
        
        assertEquals(budget.startTime, savedBudget.startTime)
        assertEquals(budget.endTime, savedBudget.endTime)
    }
    
    @Test
    @Throws(IOException::class)
    fun insertDuplicateCategoryAndPeriod_WontInsert() {
        val category = Category(categoryId = 40L, name = "Rent", defaultAmount = 450f, isActive = true)
        val budget = Budget(
            amount = 500f,
            startTime = 40L,
            endTime = 500L,
            category = category
        )
        val budget2 = budget.copy(amount = 300f)
        budgetDao.insert(budget)
        budgetDao.insert(budget2)
        
        val budgets = budgetDao.getAll()
        val savedBudget = budgets.first()
        
        assertTrue(budgets.size == 1)
        assertEquals(category, savedBudget.category)
    
        assertEquals(500f, savedBudget.amount)
        assertEquals(budget.startTime, savedBudget.startTime)
        assertEquals(budget.endTime, savedBudget.endTime)
    }
    
    @Test
    @Throws(IOException::class)
    fun updateBudget() {
        val category = Category(categoryId = 40L, name = "Rent", defaultAmount = 450f, isActive = true)
        val budget = Budget(
            amount = 500f,
            startTime = 40L,
            endTime = 500L,
            category = category
        )
        
        budgetDao.insert(budget)
        
        val budgets = budgetDao.getAll()
        val savedBudget = budgets.first()
        
        assertTrue(budgets.size == 1)
        assertEquals(category, savedBudget.category)
        assertEquals(500f, savedBudget.amount)
    
        val newBudget = savedBudget.copy(amount = 300f)
        budgetDao.update(newBudget)
        
        val updatedBudget = budgetDao.getBudgetById(savedBudget.id)
        assertEquals(newBudget.category, updatedBudget.category)
        assertEquals(newBudget.amount, updatedBudget.amount)
    }
}
