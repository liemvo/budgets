package com.vad.budgets.util

import java.text.NumberFormat

object Utility {
    val numberFormat: NumberFormat = NumberFormat.getInstance().apply {
        maximumFractionDigits = 2
    }
}
