package com.vad.budgets.ui.category

import com.vad.budgets.data.category.Category
import com.vad.budgets.util.Utility.numberFormat

fun Category.getDefaultMoney(): String {
    return "${numberFormat.format(defaultAmount)} $currency"
}
