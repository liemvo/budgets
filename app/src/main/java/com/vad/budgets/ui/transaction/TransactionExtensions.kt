package com.vad.budgets.ui.transaction

import com.vad.budgets.data.transaction.Transaction
import com.vad.budgets.util.Utility

fun Transaction.getValueAndCurrency(): String {
    return "${Utility.numberFormat.format(amount)} $currency"
}
