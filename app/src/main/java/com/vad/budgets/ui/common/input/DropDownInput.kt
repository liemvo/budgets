package com.vad.budgets.ui.common.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class DropdownModel<T>(override val title: String) : InputInterface {
    val options = MutableLiveData<List<T>>()
    val selectedOption = MediatorLiveData<T>()
    
    private val error = MutableLiveData<String>()
    
    fun update(options: List<T>, selectedOption: T) {
        this.options.postValue(options)
        this.selectedOption.postValue(selectedOption)
    }
    
    override fun showError(error: String) = this.error.postValue(error)
    override fun clearError() = error.postValue("")
    override fun getError(): LiveData<String> = error
}
