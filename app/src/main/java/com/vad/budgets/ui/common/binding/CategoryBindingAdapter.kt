package com.vad.budgets.ui.common.binding

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import com.vad.budgets.data.category.Category
import com.vad.budgets.databinding.ItemSimpleBinding

object CategoryBindingAdapter {
    @JvmStatic
    @BindingAdapter("items", requireAll = true)
    fun setTypeAdapter(view: Spinner, items: List<Category>?) {
        if (items != null) {
            val adapter = object : SpinnerAdapter<Category>() {
                override fun onViewBinding(binding: ItemSimpleBinding, item: Category) {
                    binding.name = item.name
                }
            }
            adapter.setItems(items)
            view.adapter = adapter
        }
    }

    @JvmStatic
    @InverseMethod("toCategory")
    fun toSelectedCategoryIndex(options: List<Category>?, type: Category?): Int {
        if (type != null && options != null) {
            return options.indexOf(type)
        }
        return 0
    }

    @JvmStatic
    fun toCategory(options: List<Category>, index: Int): Category {
        return options[index]
    }
}
