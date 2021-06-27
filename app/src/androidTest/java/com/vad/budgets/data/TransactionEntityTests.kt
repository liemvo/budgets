package com.vad.budgets.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vad.budgets.data.transaction.Currency
import com.vad.budgets.data.transaction.Transaction
import com.vad.budgets.data.transaction.TransactionDao
import com.vad.budgets.data.transaction.TransactionType
import com.vad.budgets.util.getOrAwaitValue
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TransactionEntityTests {
    private lateinit var transactionDao: TransactionDao
    private lateinit var db: BudgetDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, BudgetDatabase::class.java
        ).build()
        transactionDao = db.transactionDao()
    }
    
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
    
    @Test
    @Throws(IOException::class)
    fun insertTransaction() {
        val expense = Transaction(
            title = "Lunch",
            categoryName = "Rent",
            amount = 15.0f,
            currency = currency,
            transactionType = expense,
            date = 40L
        )

        runBlockingTest {
            transactionDao.insert(expense)
        }

        var transactions = transactionDao.getAll().getOrAwaitValue()
        assertEquals(1, transactions.size)
        
        val revenue = Transaction(
            title = "Sale",
            categoryName = "Business",
            amount = 15.0f,
            currency = currency,
            transactionType = revenue,
            date = 60L
        )

        runBlockingTest {
            transactionDao.insert(revenue)
        }

        transactions = transactionDao.getAll().getOrAwaitValue()
        assertEquals(2, transactions.size)
    }
    
    @Test
    @Throws(IOException::class)
    fun updateTransaction() {
        val expense = Transaction(
            title = "Lunch",
            categoryName = "Rent",
            amount = 15.0f,
            currency = currency,
            transactionType = expense,
            date = 40L
        )

        runBlockingTest {
            transactionDao.insert(expense)
        }

        var transactions = transactionDao.getAll().getOrAwaitValue()
        assertEquals(1, transactions.size)
        
        val revenue = transactions.first().copy(transactionType = revenue)
        transactionDao.update(revenue)
        
        val updatedTransaction = transactionDao.getTransactionsById(revenue.id)

        transactions = transactionDao.getAll().getOrAwaitValue()
        assertEquals(1, transactions.size)
        
        assertEquals(revenue, updatedTransaction)
    }
    
    @Test
    @Throws(IOException::class)
    fun deleteTransaction() {
        val expense = Transaction(
            title = "Lunch",
            categoryName = "Rent",
            amount = 15.0f,
            currency = currency,
            transactionType = expense,
            date = 40L
        )
        runBlockingTest {
            transactionDao.insert(expense)
        }
        var transactions = transactionDao.getAll().getOrAwaitValue()
        assertEquals(1, transactions.size)
        
        val revenue = Transaction(
            title = "Sale",
            categoryName = "Business",
            amount = 15.0f,
            currency = currency,
            transactionType = revenue,
            date = 60L
        )
        runBlockingTest {
            transactionDao.insert(revenue)
        }
        transactions = transactionDao.getAll().getOrAwaitValue()
        assertEquals(2, transactions.size)
        
        transactionDao.clearTransactions()

        transactions = transactionDao.getAll().getOrAwaitValue()
        assertTrue(transactions.isEmpty())
    }
    
    @Test
    @Throws(IOException::class)
    fun getTransactionsByPeriod() {
        val expense = Transaction(
            title = "Lunch",
            categoryName = "Rent",
            amount = 15.0f,
            currency = currency,
            transactionType = expense,
            date = 40L
        )
        
        val revenue = Transaction(
            title = "Sale",
            categoryName = "Business",
            amount = 15.0f,
            currency = currency,
            transactionType = revenue,
            date = 60L
        )
        
        val expense1 = Transaction(
            title = "Lunch",
            categoryName = "Rent",
            amount = 15.0f,
            currency = currency,
            transactionType = TransactionEntityTests.expense,
            date = 80L
        )
        runBlockingTest {
            transactionDao.insert(expense, revenue, expense1)
        }

        val transactions = transactionDao.getTransactionsByPeriod(40L, 70L).getOrAwaitValue()
        assertEquals(2, transactions.size)
        assertEquals(revenue, transactions.first())
        assertEquals(expense, transactions.last())
        
    }
    
    companion object {
        private val currency = Currency.NZD.value
        private val expense = TransactionType.EXPENSE.type
        private val revenue = TransactionType.REVENUE.type
    }
}