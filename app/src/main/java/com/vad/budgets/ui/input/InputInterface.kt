package com.vad.budgets.ui.input

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    if (errorMessage == null) {
        view.isErrorEnabled = false
        view.error = ""
    } else {
        view.error = errorMessage
        view.isErrorEnabled = true
    }
}

interface InputInterface {
    val title: String
    fun showError(error: String)
    fun clearError()
    fun getError(): LiveData<String>
}
