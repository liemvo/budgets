package com.vad.budgets.ui.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetsViewModel @Inject constructor(private val eventBus: EventBus) : ViewModel() {
    private val _date = MutableLiveData<Date?>(null)
    val date: LiveData<Date?> get() = _date
    
    init {
        eventBus.register(this)
    }
    
    public override fun onCleared() {
        eventBus.unregister(this)
        super.onCleared()
    }
    
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onTimeUpdated(selectedTime: Date) {
        _date.postValue(selectedTime)
    }
}
