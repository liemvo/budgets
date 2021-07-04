package com.vad.budgets.ui.transaction

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import java.util.Calendar
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private val args: DatePickerFragmentArgs by navArgs()
    private val calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val time = args.dateInMilliSeconds?.toLongOrNull()
        Timber.e("time: $time")

        time?.let {
            calendar.timeInMillis = it
        } ?: run { calendar.timeInMillis = System.currentTimeMillis() }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        calendar.set(year, month, day)
        EventBus.getDefault().post(SelectedTime(calendar.timeInMillis))
    }
}
