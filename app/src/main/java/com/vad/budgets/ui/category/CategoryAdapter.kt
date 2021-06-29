package com.vad.budgets.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vad.budgets.data.category.Category
import com.vad.budgets.databinding.ItemCategoryBinding
import com.vad.budgets.ui.common.ItemClickListener
import com.vad.budgets.ui.common.diff.CommonDiffUtil

class CategoryAdapter(
    private val itemClickListener: ItemClickListener,
) : RecyclerView.Adapter<CategoryAdapter.Companion.CategoryHolder>() {
    private val items = mutableListOf<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryHolder(
        ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        itemClickListener
    )

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updateList(newItems: List<Category>) {
        val result = DiffUtil.calculateDiff(CommonDiffUtil(items, newItems))
        items.clear()
        items.addAll(newItems)
        result.dispatchUpdatesTo(this)
    }

    companion object {
        class CategoryHolder(
            private val binding: ItemCategoryBinding,
            private val itemClickListener: ItemClickListener,
        ) : RecyclerView.ViewHolder(binding.root) {

            init {
                binding.root.setOnClickListener {
                    binding.category?.name?.let { name -> itemClickListener.onItemClick(name) }
                }
            }

            fun bind(category: Category) {
                binding.category = category
                binding.executePendingBindings()
            }
        }
    }
}
