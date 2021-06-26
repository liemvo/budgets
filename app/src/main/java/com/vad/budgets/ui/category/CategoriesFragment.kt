package com.vad.budgets.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vad.budgets.databinding.FragmentCategoriesBinding
import javax.inject.Inject

class CategoriesFragment : Fragment() {
    
    @Inject
    lateinit var categoriesViewModel: CategoriesViewModel
    
    private lateinit var binding: FragmentCategoriesBinding
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCategoriesBinding.inflate(inflater, container, false).apply {
            binding = this
            lifecycleOwner = viewLifecycleOwner
            viewModel = categoriesViewModel
        }.root
}
