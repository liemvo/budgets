package com.vad.budgets.di

import android.content.Context
import com.vad.budgets.BudgetApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: BudgetApplication) {
    @Provides
    @Singleton
    fun provideContext(): Context = application
    
    @Provides
    @Singleton
    fun provideApplication(): BudgetApplication = application
}
