package com.vad.budgets.util

import java.text.DateFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import org.joda.time.DateTime

object Utility {
    private val calendar = Calendar.getInstance()
    private const val DATE_FORMAT = "MMM dd, YYYY"
    private const val MONTH_YEAR = "MMMM, YYYY"

    val pickerItems by lazy {
        (0..150).map {
            DateTime.now().minusMonths(it).toDate()
        }
    }

    val numberFormat: NumberFormat = NumberFormat.getInstance().apply {
        maximumFractionDigits = 2
    }

    val months = DateFormatSymbols.getInstance().months.filterNot { it.isEmpty() }

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

        val localMonth = month ?: calendar.get(Calendar.MONTH) - 1
        calendar.set(
            calendar.get(Calendar.YEAR),
            localMonth,
            calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 1
        )
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        return calendar.time
    }

    fun Date.format(pattern: String = DATE_FORMAT): String {
        val format = SimpleDateFormat(pattern, Locale.getDefault())
        return format.format(this)
    }

    fun Date.formatMonthYear(): String = format(MONTH_YEAR)

    fun dateFrom(milliSecond: Long): Date = Date().apply {
        time = milliSecond
    }
}
