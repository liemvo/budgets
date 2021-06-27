package com.vad.budgets.ui.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class TextFieldInput(override val title: String) : InputInterface {
    val disabled = MutableLiveData(false)
    val text = MediatorLiveData<String>()
    
    private val error = MutableLiveData("")
    
    fun update(value: String) = text.postValue(value)
    
    override fun showError(error: String) {
        this.error.postValue(error)
    }
    
    override fun clearError() {
        error.postValue("")
    }
    
    override fun getError(): LiveData<String> = error
}