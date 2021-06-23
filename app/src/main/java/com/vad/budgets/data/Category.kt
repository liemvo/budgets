package com.vad.budgets.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "categories", indices = [Index(value = ["name"], unique = true)])
data class Category(
    @PrimaryKey(autoGenerate = true)
    var categoryId: Long = 0,
    val name: String,
    val defaultAmount: Float,
    val isActive: Boolean
)
