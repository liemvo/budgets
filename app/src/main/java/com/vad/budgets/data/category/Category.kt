package com.vad.budgets.data.category

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vad.budgets.ui.common.diff.DiffInterface

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val name: String,
    val defaultAmount: Float,
    val currency: String,
    val isActive: Boolean
): DiffInterface {
    override val diffIdentify: Any
        get() = name
}
