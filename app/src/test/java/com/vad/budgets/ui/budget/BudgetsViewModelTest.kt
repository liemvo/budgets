package com.vad.budgets.ui.budget

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.vad.budgets.util.observeForTesting
import org.greenrobot.eventbus.EventBus
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.sql.Date


@RunWith(MockitoJUnitRunner::class)
class BudgetsViewModelTest {
    @Mock
    private lateinit var eventBus: EventBus
    
    private lateinit var budgetsViewModel: BudgetsViewModel
    
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    
    @Test
    fun `verify eventBus is register on initial viewmodel`() {
        budgetsViewModel = BudgetsViewModel(eventBus)
        verify(eventBus).register(budgetsViewModel)
    }
    
    @Test
    fun `verify eventBus is unregister on clear viewmodel`() {
        budgetsViewModel = BudgetsViewModel(eventBus)
        verify(eventBus).register(budgetsViewModel)
        
        budgetsViewModel.onCleared()
        verify(eventBus).unregister(budgetsViewModel)
    }
    
    @Test
    fun `verify on time update invoke`() {
        budgetsViewModel = BudgetsViewModel(eventBus)
        verify(eventBus).register(budgetsViewModel)
        
        val date = Date.valueOf("2021-08-12")
        budgetsViewModel.onTimeUpdated(date)
        
        budgetsViewModel.date.observeForTesting {
            assertEquals(date, budgetsViewModel.date.value)
        }
    }
}
