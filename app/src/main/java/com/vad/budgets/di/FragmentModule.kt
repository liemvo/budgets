package com.vad.budgets.di

import com.vad.budgets.ui.budget.BudgetsFragment
import com.vad.budgets.ui.budget.monthyear.MonthYearPickerFragment
import com.vad.budgets.ui.category.CategoriesFragment
import com.vad.budgets.ui.category.details.CategoryFragment
import com.vad.budgets.ui.transaction.DatePickerFragment
import com.vad.budgets.ui.transaction.TransactionsFragment
import com.vad.budgets.ui.transaction.details.TransactionDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeCategoriesFragment(): CategoriesFragment
    
    @ContributesAndroidInjector
    abstract fun contributeCategoryFragment(): CategoryFragment
    
    @ContributesAndroidInjector
    abstract fun contributeBudgetsFragment(): BudgetsFragment
    
    @ContributesAndroidInjector
    abstract fun contributeTransactionsFragment(): TransactionsFragment

    @ContributesAndroidInjector
    abstract fun contributeTransactionDetailsFragment(): TransactionDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeDatePickerFragment(): DatePickerFragment

    @ContributesAndroidInjector
    abstract fun contributeMonthYearPickerFragment(): MonthYearPickerFragment
}
