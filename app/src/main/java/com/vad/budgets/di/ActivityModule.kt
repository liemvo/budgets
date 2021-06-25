package com.vad.budgets.di

import com.vad.budgets.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
