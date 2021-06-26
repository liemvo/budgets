package com.vad.budgets.ui.category.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.vad.budgets.R
import com.vad.budgets.ui.actionbar.BaseFragment
import javax.inject.Inject

class CategoryFragment : BaseFragment() {
    @Inject
    lateinit var viewModel: CategoryViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        // TODO: Use the ViewModel
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
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
