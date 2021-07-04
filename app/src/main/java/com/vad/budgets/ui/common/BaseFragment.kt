package com.vad.budgets.ui.common

import android.content.Context
import androidx.fragment.app.Fragment
import com.vad.budgets.ui.common.actionbar.ActionBarController
import com.vad.budgets.ui.common.navigation.NavigationFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

open class BaseFragment : Fragment(), NavigationFragment, HasAndroidInjector {
    protected val nonNullContext by lazy {
        requireContext()
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    val actionBarController by lazy {
        requireActivity() as? ActionBarController
    }
    
    override fun onDestroy() {
        actionBarController?.updateNavigationIcon(null)
        super.onDestroy()
    }
    
    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
    
    override fun androidInjector(): AndroidInjector<Any?>? = androidInjector
}
