package com.vad.budgets.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Singleton

@Singleton
class TransactionsViewModel : ViewModel() {
    
    private val _text = MutableLiveData<String>().apply {
        value = "This is transactions Fragment"
    }
    val text: LiveData<String> = _text
}