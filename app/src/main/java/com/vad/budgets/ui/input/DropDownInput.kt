package com.vad.budgets.ui.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DropdownModel<T>(override val title: String) : InputInterface {
    val options = MutableLiveData<List<T>>()
    val selectedOption = MutableLiveData<T>()
    
    private val error = MutableLiveData<String>()
    
    fun update(options: List<T>, selectedOption: T) {
        this.options.postValue(options)
        this.selectedOption.postValue(selectedOption)
    }
    
    
    override fun showError(error: String) = this.error.postValue(error)
    override fun clearError() = error.postValue("")
    override fun getError(): LiveData<String> = error
}
