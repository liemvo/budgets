package com.vad.budgets.ui.common.diff

import androidx.recyclerview.widget.DiffUtil

class CommonDiffUtil(private val oldItems: List<DiffInterface>, private val newItems: List<DiffInterface>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldItems.size
    override fun getNewListSize() = newItems.size
    
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition].diffIdentify == newItems[newItemPosition].diffIdentify
    
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition] == newItems[newItemPosition]
}
