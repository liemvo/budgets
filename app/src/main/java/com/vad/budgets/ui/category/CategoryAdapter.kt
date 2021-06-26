package com.vad.budgets.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vad.budgets.data.category.Category
import com.vad.budgets.databinding.ItemCategoryBinding
import com.vad.budgets.di.scope.CategoryScope
import javax.inject.Inject

@CategoryScope
class CategoryAdapter  @Inject constructor(): RecyclerView.Adapter<CategoryAdapter.Companion.CategoryHolder>() {
    private val items = mutableListOf<Category>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryHolder(
        ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    
    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(items[position])
    }
    
    override fun getItemCount() = items.size
    
    fun getItem(position: Int) = items[position]
    
    fun updateList(newItems: List<Category>) {
        val result  = DiffUtil.calculateDiff(CategoryDiffUtil(items, newItems))
        items.clear()
        items.addAll(newItems)
        result.dispatchUpdatesTo(this)
    }
    
    companion object {
        class CategoryHolder(
            private val binding: ItemCategoryBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            
            fun bind(category: Category) {
                binding.category = category
                binding.executePendingBindings()
            }
        }
    }
}
