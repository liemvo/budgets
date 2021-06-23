package com.vad.budgets.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    var categoryId: Long = 0,
    val name: String,
    val defaultAmount: Float,
    val isActive: Boolean
) {
    
    companion object {
        const val CATEGORY_COLUMN_NAME = "name"
        const val CATEGORY_COLUMN_DEFAULT_AMOUNT = "defaultAmount"
        const val CATEGORY_COLUMN_ACTIVE = "isActive"
        const val CATEGORY_COLUMN_ID = "categoryId"
    }
}
