package com.vad.budgets.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.vad.budgets.ui.InjectingFragmentFactory
import com.vad.budgets.ui.budget.BudgetsFragment
import com.vad.budgets.ui.category.CategoriesFragment
import com.vad.budgets.ui.category.details.CategoryFragment
import com.vad.budgets.ui.transaction.TransactionsFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FragmentBindingModule {
    @Binds
    @IntoMap
    @FragmentKey(CategoriesFragment::class)
    abstract fun bindCategoriesFragment(categoriesFragment: CategoriesFragment): Fragment
    
    @ContributesAndroidInjector
    abstract fun contributeCategoryFragment(): CategoryFragment
    
    @Binds
    @IntoMap
    @FragmentKey(BudgetsFragment::class)
    abstract fun bindBudgetsFragment(budgetsFragment: BudgetsFragment): Fragment
    
    @Binds
    @IntoMap
    @FragmentKey(TransactionsFragment::class)
    abstract fun bindTransactionsFragment(transactionsFragment: TransactionsFragment): Fragment
    
    @Binds
    abstract fun bindFragmentFactory(factory: InjectingFragmentFactory): FragmentFactory
}
