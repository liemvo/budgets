package com.vad.budgets.ui.category

import androidx.recyclerview.widget.DiffUtil
import com.vad.budgets.data.category.Category

class CategoryDiffUtil(private val oldItems: List<Category>, private val newItems: List<Category>): DiffUtil.Callback() {
    override fun getOldListSize() = oldItems.size
    override fun getNewListSize() = newItems.size
    
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldItems[oldItemPosition].name == newItems[newItemPosition].name
    
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldItems[oldItemPosition] == newItems[newItemPosition]
}
