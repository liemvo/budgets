package com.vad.budgets.di

import com.vad.budgets.data.category.CategoryRepository
import com.vad.budgets.data.category.CategoryRepositoryImpl
import com.vad.budgets.di.scope.CategoryScope
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    @CategoryScope
    abstract fun provideCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository
}
