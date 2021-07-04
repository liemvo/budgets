package com.vad.budgets.ui.transaction

import com.vad.budgets.data.transaction.Transaction
import com.vad.budgets.util.Utility
import com.vad.budgets.util.Utility.format

fun Transaction.getValueAndCurrency(): String {
    return "${Utility.numberFormat.format(amount)} $currency"
}

fun Transaction.amountInString(): String = Utility.numberFormat.format(amount)

fun Transaction.dateInString(): String = Utility.dateFrom(date).format()

fun String?.nonNull(): String = this ?: ""
