package com.vad.budgets.ui.common

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


open class BaseDialogFragment : DialogFragment(), HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any?>? = androidInjector

    override fun onResume() {
        super.onResume()

        dialog?.window?.let { window ->
            val width = Resources.getSystem().displayMetrics.widthPixels
            window.setLayout((width * 0.90).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
            window.setGravity(Gravity.CENTER)
        }
    }
}
