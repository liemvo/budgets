package com.vad.budgets.ui.category.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.vad.budgets.R
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
            val name = CategoryFragmentArgs.fromBundle(it).categoryName
            val titleId = if (name == null) {
                viewModel.resetFields()
                R.string.title_add_category
            } else {
                viewModel.loadCategory(name)
                R.string.title_edit_category
            }
            actionBarController?.setTitle(titleId)
        }
        setHasOptionsMenu(true)
        viewModel.isFinished.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.clearSaveOrUpdateState()
                actionBarController?.onBackPressed()
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        actionBarController?.updateNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        actionBarController?.onNavigationIconClicked {
            viewModel.clearSaveOrUpdateState()
            actionBarController?.onBackPressed()
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_save, menu)
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {
            viewModel.saveOrUpdateCategory()
        }
        return super.onOptionsItemSelected(item)
    }
}
