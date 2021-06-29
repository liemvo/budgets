package com.vad.budgets

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.vad.budgets.databinding.ActivityMainBinding
import com.vad.budgets.ui.common.actionbar.ActionBarController
import com.vad.budgets.ui.common.navigation.NavManager
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector, ActionBarController {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    
    private val navigationManager by lazy {
        NavManager(
            activity = this,
            hostId = R.id.nav_host_fragment
        )
    }
    
    private lateinit var binding: ActivityMainBinding
    
    private val isActivityReady get() = ::binding.isInitialized
    
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        setSupportActionBar(binding.toolBar)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_transactions, R.id.navigation_categories, R.id.navigation_budgets
            )
        )
        
        setupActionBarWithNavController(navigationManager.controller, appBarConfiguration)
        binding.navView.setupWithNavController(navigationManager.controller)
    }
    
    override fun androidInjector(): AndroidInjector<Any> = androidInjector
    
    override fun onBackPressed() {
        navigationManager.onBackPressed {
            super.onBackPressed()
        }
    }
    
    override fun setTitle(@StringRes titleId: Int?) {
        titleId?.let {
            if (isActivityReady) {
                binding.title = getString(it)
            }
        }
    }
    
    override fun onNavigationIconClicked(callback: () -> Unit) {
        binding.toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
