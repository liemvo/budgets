package com.vad.budgets.ui.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vad.budgets.data.category.Category
import com.vad.budgets.data.transaction.Transaction
import com.vad.budgets.databinding.ItemTransactionBinding
import com.vad.budgets.ui.common.ItemClickListener
import com.vad.budgets.ui.common.diff.CommonDiffUtil

class TransactionAdapter(
    private val itemClickListener: ItemClickListener,
) : RecyclerView.Adapter<TransactionAdapter.Companion.TransactionHolder>() {
    private val items = mutableListOf<Transaction>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TransactionHolder(
        ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        itemClickListener
    )

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newItems: List<Transaction>) {
        val result = DiffUtil.calculateDiff(CommonDiffUtil(items, newItems))
        items.clear()
        items.addAll(newItems)
        result.dispatchUpdatesTo(this)
    }

    companion object {
        class TransactionHolder(
            private val binding: ItemTransactionBinding,
            private val itemClickListener: ItemClickListener,
        ) : RecyclerView.ViewHolder(binding.root) {

            init {
                binding.root.setOnClickListener {
                    binding.transaction?.id?.let{ id -> itemClickListener.onItemClick(id) }
                }
            }

            fun bind(transaction: Transaction) {
                binding.transaction = transaction
                binding.executePendingBindings()
            }
        }
    }
}
