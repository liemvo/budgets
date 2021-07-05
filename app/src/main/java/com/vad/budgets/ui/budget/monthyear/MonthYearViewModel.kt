package com.vad.budgets.ui.budget.monthyear

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vad.budgets.util.Utility
import com.vad.budgets.util.Utility.formatMonthYear
import javax.inject.Inject
import javax.inject.Singleton
import org.greenrobot.eventbus.EventBus

@Singleton
class MonthYearViewModel @Inject constructor(private val eventBus: EventBus) : ViewModel() {
    val minValue: Int = 0
    val data by lazy { Utility.pickerItems.map { it.formatMonthYear() } }

    private val _item = MutableLiveData<Int>()
    val item: LiveData<Int> = _item

    init {
        _item.value = minValue
    }

    fun onDataChange(newValue: Int) {
        _item.value = newValue
    }

    fun notifySelectedDate() {
        _item.value?.let {
            eventBus.post(Utility.pickerItems[it])
        }
    }
}
