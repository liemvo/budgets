package com.vad.budgets.di

import com.vad.budgets.ui.InjectingNavHostFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NavHostModule {
    
    @ContributesAndroidInjector(modules = [FragmentBindingModule::class])
    abstract fun navHostFragmentInjector(): InjectingNavHostFragment
}