package com.vad.budgets.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vad.budgets.R

class CategoriesFragment : Fragment() {
    
    private lateinit var categoriesViewModel: CategoriesViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoriesViewModel =
            ViewModelProvider(this).get(CategoriesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_categories, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        categoriesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}