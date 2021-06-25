package com.vad.budgets.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vad.budgets.data.budget.Budget
import com.vad.budgets.data.budget.BudgetDao
import com.vad.budgets.data.category.Category
import com.vad.budgets.data.transaction.Currency
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
    private lateinit var db: BudgetDatabase
    
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, BudgetDatabase::class.java
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
        val category = Category(name = "Rent", defaultAmount = 450f, isActive = true, currency = currency)
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
        val category = Category(name = "Rent", defaultAmount = 450f, isActive = true, currency = currency)
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
        val category = Category(name = "Rent", defaultAmount = 450f, isActive = true, currency = currency)
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
        
        val updatedBudget = budgetDao.getBudget(savedBudget.category.name, savedBudget.startTime, savedBudget.endTime)
        assertEquals(newBudget.category, updatedBudget.category)
        assertEquals(newBudget.amount, updatedBudget.amount)
    }
    
    @Test
    @Throws(IOException::class)
    fun clearDatabase() {
        insertBudgets()
        
        budgetDao.clearBudgets()
        
        assertTrue(budgetDao.getAll().isEmpty())
    }
    
    @Test
    @Throws(IOException::class)
    fun insertAllBudgets() {
        
        insertBudgets()
        
        val allBudgets = budgetDao.getAll()
        
        assertEquals(3, allBudgets.size)
    }
    
    @Test
    @Throws(IOException::class)
    fun getAllBudgetsInPeriods() {
        insertBudgets()
        
        val budgets = budgetDao.getAllBudgetsInPeriod(40L, 500L)
        
        assertEquals(2, budgets.size)
    }
    
    private fun insertBudgets() {
        val category1 = Category(name = "Rent", defaultAmount = 450f, isActive = true, currency = currency)
        val budget1 = Budget(
            amount = 500f,
            startTime = 40L,
            endTime = 500L,
            category = category1
        )
    
        val category2 = Category(name = "Award", defaultAmount = 500f, isActive = true, currency = currency)
        val budget2 = Budget(
            amount = 500f,
            startTime = 40L,
            endTime = 500L,
            category = category2
        )
    
        val budget3 = Budget(
            amount = 500f,
            startTime = 501L,
            endTime = 900L,
            category = category2
        )
    
        budgetDao.insertBudgets(budget1, budget2, budget3)
    }
    
    companion object {
        private val currency = Currency.NZD.value
    }
}
