package com.vad.budgets.di

import android.app.Application
import com.vad.budgets.BudgetApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, ActivityModule::class, AndroidInjectionModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        
        fun appModule(appModule: AppModule): Builder
        
        fun build(): AppComponent
    }
    
    fun inject(app: BudgetApplication)
}
