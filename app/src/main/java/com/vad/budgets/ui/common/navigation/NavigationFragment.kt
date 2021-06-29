package com.vad.budgets.ui.common.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

interface NavigationFragment {
    fun onBackPressed() = false
    
    fun NavDirections.navigateSafe(options: NavOptions? = null) {
        (this@NavigationFragment as? Fragment)?.findNavController()?.let { controller ->
            this.navigateSafe(controller, options)
        }
    }
    
    private fun NavDirections.navigateSafe(controller: NavController, options: NavOptions? = null) {
        val actionExists = controller.currentDestination?.getAction(this.actionId) != null
        if (actionExists) {
            controller.navigate(this, options)
        }
    }
}
