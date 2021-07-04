package com.vad.budgets.ui.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetsViewModel @Inject constructor() : ViewModel() {
    
    private val _text = MutableLiveData<String>().apply {
        value = "This is budgets history Fragment"
    }
    val text: LiveData<String> = _text
}