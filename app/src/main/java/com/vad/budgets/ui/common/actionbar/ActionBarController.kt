package com.vad.budgets.ui.common.actionbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface ActionBarController {
    fun setTitle(@StringRes titleId: Int?)
    fun onNavigationIconClicked(callback: () -> Unit)
    fun updateNavigationIcon(@DrawableRes drawable: Int?) {}
    fun onBackPressed()
}
