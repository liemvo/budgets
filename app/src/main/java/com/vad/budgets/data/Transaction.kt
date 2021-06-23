package com.vad.budgets.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
class Transaction(
    val name: String,
    val budgetId: Int,
    val amount: Float,
    @Embedded val currency: Currency,
    @Embedded val transactionType: TransactionType,
    val note: String? = null,
    val date: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}


enum class TransactionType(val type: Int) {
    EXPENSE(1),
    REVENUE(2)
}

enum class Currency(val currency: String) {
    USD("USD"),
    NZD("NZD")
}
