package com.vad.budgets.ui.common.binding

import android.content.Context
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import com.vad.budgets.R
import com.vad.budgets.data.transaction.TransactionType
import com.vad.budgets.databinding.ItemSimpleBinding

object TypeBindingAdapter {
    @JvmStatic
    @BindingAdapter("types", requireAll = true)
    fun setTypeAdapter(view: Spinner, items: List<TransactionType>?) {
        if (items != null) {
            val adapter = object : SpinnerAdapter<TransactionType>() {
                override fun onViewBinding(binding: ItemSimpleBinding, item: TransactionType) {
                    binding.name = item.getString(view.context)
                }
            }
            adapter.setItems(items)
            view.adapter = adapter
        }
    }

    @JvmStatic
    @InverseMethod("toTransactionType")
    fun toSelectedTransactionTypeIndex(
        options: List<TransactionType>?,
        type: TransactionType?,
    ): Int {
        if (type != null && options != null) {
            return options.indexOf(type)
        }
        return 0
    }

    @JvmStatic
    fun toTransactionType(options: List<TransactionType>, index: Int): TransactionType {
        return options[index]
    }
}

private fun TransactionType.getString(context: Context): String = when (this) {
    is TransactionType.EXPENSE -> context.getString(R.string.transaction_type_expense)
    is TransactionType.REVENUE -> context.getString(R.string.transaction_type_revenue)
}
