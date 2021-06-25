package com.vad.budgets.data

import com.vad.budgets.data.transaction.Currency
import com.vad.budgets.data.transaction.TransactionType

fun Int?.toTransactionType(): TransactionType? = this?.let {
    when(it) {
        TransactionType.EXPENSE.type -> TransactionType.EXPENSE
        TransactionType.REVENUE.type -> TransactionType.REVENUE
        else -> null
    }
}


fun String?.toCurrency(): Currency? = this?.let {
    when(it) {
        Currency.NZD.value -> Currency.NZD
        Currency.USD.value -> Currency.USD
        else -> null
    }
}
