package com.vad.budgets.data.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vad.budgets.ui.common.diff.DiffInterface

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val title: String,
    val categoryName: String,
    val accountName: String? = null,
    val amount: Float,
    val currency: String,
    val transactionType: Int,
    val note: String? = null,
    val date: Long,
) : DiffInterface {
    override fun equals(other: Any?): Boolean {
        val otherTransaction = other as? Transaction
        if (otherTransaction != null) {
            return title == otherTransaction.title
                    && categoryName == otherTransaction.categoryName
                    && amount == otherTransaction.amount
                    && accountName == otherTransaction.accountName
                    && currency == otherTransaction.currency
                    && transactionType == otherTransaction.transactionType
                    && note == otherTransaction.note
                    && date == otherTransaction.date
        }
        return false
    }

    val isExpense get() = transactionType == TransactionType.EXPENSE.type

    override val diffIdentify: Any
        get() = id
}

sealed class TransactionType(val type: Int) {
    object EXPENSE : TransactionType(1)
    object REVENUE : TransactionType(2)
}

sealed class Currency(val value: String) {
    object USD : Currency("USD")
    object NZD : Currency("NZD")
}

object StaticData {
    val currencies = listOf(Currency.NZD, Currency.USD)
    val transactionTypes = listOf(TransactionType.EXPENSE, TransactionType.REVENUE)
}
