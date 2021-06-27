package com.vad.budgets.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


object Utility {
    private val calendar = Calendar.getInstance()
    private const val DATE_FORMAT = "MMM, dd YYYY"

    val numberFormat: NumberFormat = NumberFormat.getInstance().apply {
        maximumFractionDigits = 2
    }

    fun getFirstDateTimeOfMonth(month: Int?, year: Int? = null): Date {
        calendar.time = Date()
        year?.let {
            calendar.set(Calendar.YEAR, it)
        }
        month?.let {
            calendar.set(Calendar.MONTH, month)
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        return calendar.time
    }

    fun getLastDateTimeOfMonth(month: Int?, year: Int? = null): Date {
        calendar.time = Date()
        year?.let {
            calendar.set(Calendar.YEAR, it)
        }

        val localMonth = month ?: calendar.get(Calendar.MONTH)
        calendar.set(calendar.get(Calendar.YEAR), localMonth,
            calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        return calendar.time
    }

    fun Date.format(pattern: String = DATE_FORMAT): String {
        val format = SimpleDateFormat(pattern, Locale.getDefault())
        return format.format(this)
    }
}
