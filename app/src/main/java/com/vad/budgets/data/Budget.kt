package com.vad.budgets.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class Budget(
    val amount: Float,
    val expense: Float = 0.0f,
    val revenue: Float = 0.0f,
    val startTime: Long,
    val endTime: Long,
    @Embedded val category: Category
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
