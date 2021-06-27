package com.vad.budgets.ui.category.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.vad.budgets.R
import com.vad.budgets.databinding.FragmentCategoriesBinding
import com.vad.budgets.databinding.FragmentCategoryBinding
import com.vad.budgets.ui.actionbar.BaseFragment
import javax.inject.Inject

class CategoryFragment : BaseFragment() {
    @Inject
    lateinit var viewModel: CategoryViewModel
    
    private lateinit var binding: FragmentCategoryBinding
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@CategoryFragment.viewModel
        }
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
        
        }
        actionBarController?.setTitle(R.string.title_add_category)
    }
    
    override fun onResume() {
        super.onResume()
        actionBarController?.updateNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        actionBarController?.onNavigationIconClicked {
            actionBarController?.onBackPressed()
        }
    }
}
