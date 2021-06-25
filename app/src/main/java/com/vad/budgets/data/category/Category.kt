package com.vad.budgets.data.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val name: String,
    val defaultAmount: Float,
    val currency: String,
    val isActive: Boolean
)
