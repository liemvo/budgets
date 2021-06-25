package com.vad.budgets.data.budget

import androidx.room.Embedded
import androidx.room.Entity
import com.vad.budgets.data.category.Category

@Entity(tableName = "budgets", primaryKeys = ["startTime", "endTime", "name"])
data class Budget(
    val amount: Float,
    val expense: Float = 0.0f,
    val revenue: Float = 0.0f,
    val startTime: Long,
    val endTime: Long,
    @Embedded val category: Category
)
