package com.vad.budgets.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "budgets", indices = [Index(value = ["startTime", "endTime"], unique = true)])
data class Budget(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val amount: Float,
    val expense: Float = 0.0f,
    val revenue: Float = 0.0f,
    val startTime: Long,
    val endTime: Long,
    @Embedded val category: Category
)
