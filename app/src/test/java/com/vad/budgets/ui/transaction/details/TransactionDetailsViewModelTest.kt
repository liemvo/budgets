package com.vad.budgets.ui.transaction.details

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.vad.budgets.R
import com.vad.budgets.data.repository.TransactionDetailsRepository
import com.vad.budgets.data.transaction.Currency
import com.vad.budgets.data.transaction.Transaction
import com.vad.budgets.data.transaction.TransactionType
import com.vad.budgets.util.getOrAwaitValue
import com.vad.budgets.util.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.greenrobot.eventbus.EventBus
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TransactionDetailsViewModelTest {

    @Mock
    private lateinit var application: Application

    @Mock
    private lateinit var repository: TransactionDetailsRepository

    @Mock
    private lateinit var eventBus: EventBus

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var transactionDetailsViewModel: TransactionDetailsViewModel

    @Before
    fun setUp() {
        initMock()
        transactionDetailsViewModel = TransactionDetailsViewModel(application, repository, eventBus)
    }

    private fun initMock() {
        whenever(application.getString(R.string.transaction_name)).thenReturn(TRANSACTION_NAME)
        whenever(application.getString(R.string.transaction_account)).thenReturn(TRANSACTION_ACCOUNT)
        whenever(application.getString(R.string.transaction_value)).thenReturn(TRANSACTION_VALUE)
        whenever(application.getString(R.string.transaction_note)).thenReturn(TRANSACTION_NOTE)
        whenever(application.getString(R.string.currency)).thenReturn(TRANSACTION_CURRENCY)
        whenever(application.getString(R.string.title_budgets)).thenReturn(TRANSACTION_BUDGETS)
        whenever(application.getString(R.string.transaction_type)).thenReturn(TRANSACTION_TYPE)
    }

    @After
    fun tearDown() {
        verifyMock()
        verifyNoMoreInteractions(application, repository, eventBus)
    }

    @Test
    fun wontInvokeGetTransactionDetails() {
        transactionDetailsViewModel.getExistTransaction(-1)

        transactionDetailsViewModel.categoryDropDownModel.options.observeForTesting {
            verify(repository).getCategories(false)
        }
    }

    @Test
    fun getTransactionDetails() = runBlockingTest {
        val id = 6L
        val transaction = Transaction(
            title = "Sale",
            categoryName = "Business",
            amount = 15.0f,
            currency = currency,
            transactionType = expense,
            date = 60L
        )

        whenever(repository.getTransaction(id)).doReturn(transaction)

        transactionDetailsViewModel.getExistTransaction(id)

        verify(repository).getCategories(false)
        verify(repository).getTransaction(id)
        transactionDetailsViewModel.transaction.observeForTesting {
            assertEquals(transaction, transactionDetailsViewModel.transaction.value)
        }
    }

    private fun verifyMock() {
        verify(eventBus).register(transactionDetailsViewModel)
        verify(application).getString(R.string.transaction_name)
        verify(application).getString(R.string.transaction_account)
        verify(application).getString(R.string.transaction_value)
        verify(application).getString(R.string.transaction_note)
        verify(application).getString(R.string.currency)
        verify(application).getString(R.string.title_budgets)
        verify(application).getString(R.string.transaction_type)
    }

    companion object {
        private const val TRANSACTION_NAME = "Transaction name"
        private const val TRANSACTION_ACCOUNT = "Transaction account"
        private const val TRANSACTION_VALUE = "Transaction value"
        private const val TRANSACTION_NOTE = "Transaction note"
        private const val TRANSACTION_CURRENCY = "Transaction currency"
        private const val TRANSACTION_BUDGETS = "Transaction budget"
        private const val TRANSACTION_TYPE = "Transaction type"

        private val currency = Currency.NZD.value
        private val expense = TransactionType.EXPENSE.type
    }
}
