package com.vad.budgets.ui.binding

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import com.vad.budgets.data.transaction.Currency
import com.vad.budgets.databinding.ItemSimpleBinding

object CurrencyBindingAdapter {
    @JvmStatic
    @BindingAdapter("items", requireAll = true)
    fun setTypeAdapter(view: Spinner, items: List<Currency>?) {
        if(items != null) {
            val adapter = object : SpinnerAdapter<Currency>() {
                override fun onViewBinding(binding: ItemSimpleBinding, item: Currency) {
                    binding.name = item.value
                }
            }
            adapter.setItems(items)
            view.adapter = adapter
        }
    }
    
    @JvmStatic
    @InverseMethod("toCurrency")
    fun toSelectedCurrencyIndex(options: List<Currency>?, type: Currency?): Int {
        if (type != null && options != null) {
            return options.indexOf(type)
        }
        return 0
    }
    
    @JvmStatic
    fun toCurrency(options: List<Currency>, index: Int): Currency {
        return options[index]
    }
}

abstract class SpinnerAdapter<T> : BaseAdapter() {
    
    private var internalItems: List<T> = listOf()
    
    abstract fun onViewBinding(binding: ItemSimpleBinding, item: T)
    
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val context = parent.context
        val binding: ItemSimpleBinding = ItemSimpleBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        onViewBinding(binding = binding, item = internalItems[position])
        return binding.root
    }
    
    override fun getItem(position: Int): T {
        return internalItems[position]
    }
    
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    
    override fun getCount(): Int {
        return internalItems.count()
    }
    
    open fun setItems(items: List<T>) {
        this.internalItems = items
        notifyDataSetChanged()
    }
}
