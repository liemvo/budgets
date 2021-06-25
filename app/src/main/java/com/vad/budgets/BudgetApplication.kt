package com.vad.budgets

import android.app.Activity
import android.app.Application
import com.vad.budgets.di.AppModule
import com.vad.budgets.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class BudgetApplication: Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    
    override fun activityInjector() = activityInjector
    
    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .appModule(AppModule(this))
            .build()
            .inject(this)
        
    }
}
