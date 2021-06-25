package com.vad.budgets.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.vad.budgets.data.BudgetDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    companion object {
        private const val databaseName = "budget_database"
    }
    
    @Provides
    @Singleton
    fun provideItemDatabase(context: Context): BudgetDatabase = Room.databaseBuilder(
        context.applicationContext,
        BudgetDatabase::class.java,
        databaseName
    ).build()
    
    @Provides
    @Singleton
    fun provideGson() = Gson()
}
