package com.vad.budgets.ui.navigation

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDestination
import androidx.navigation.findNavController

class NavManager(
    private val activity: AppCompatActivity,
    @IdRes private val hostId: Int
) {
    private val fragmentManager
        get() = activity.supportFragmentManager.findFragmentById(hostId)?.childFragmentManager
    
    private val currentFragment: Fragment?
        get() = fragmentManager?.primaryNavigationFragment
    
    private val currentNavigationFragment: NavigationFragment?
        get() = currentFragment as? NavigationFragment
    
    val controller
        get() = activity.findNavController(hostId)
    
    fun onBackPressed(fallback: (isRoot: Boolean) -> Unit) {
        val currentFragment = currentNavigationFragment
        if (currentFragment == null) {
            fallback(false)
        } else {
            val handled = currentFragment.onBackPressed()
            
            if (!handled) {
                if (isRootDestination(destination = controller.currentDestination)) {
                    fallback(true)
                } else {
                    controller.navigateUp()
                }
            }
        }
    }
    
    private fun isRootDestination(destination: NavDestination?): Boolean =
        destination?.id == controller.graph.startDestination
    
    fun popToRoot() = controller.popBackStack(controller.graph.startDestination, false)
}