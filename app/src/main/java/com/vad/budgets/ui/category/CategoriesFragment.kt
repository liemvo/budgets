package com.vad.budgets.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vad.budgets.R
import com.vad.budgets.databinding.FragmentCategoriesBinding
import com.vad.budgets.ui.actionbar.BaseFragment
import javax.inject.Inject

class CategoriesFragment @Inject constructor(
    private val categoriesViewModel: CategoriesViewModel
) : BaseFragment(), CategoryInterface {
    
    private val adapter by lazy {
        CategoryAdapter(this)
    }
    
    private lateinit var binding: FragmentCategoriesBinding
    
    private val _context by lazy {
        requireContext()
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCategoriesBinding.inflate(inflater, container, false).apply {
        binding = this
        lifecycleOwner = viewLifecycleOwner
        viewModel = categoriesViewModel
    }.root
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DividerItemDecoration(_context, LinearLayoutManager.VERTICAL).run {
            binding.categories.addItemDecoration(this)
        }
        actionBarController?.setTitle(R.string.title_categories)
        
        setHasOptionsMenu(true)
        
        binding.categories.adapter = adapter
        
        categoriesViewModel.categories.observe(viewLifecycleOwner, {
            adapter.updateList(it)
        })
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_add, menu)
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add) {
            navigateToCategory()
        }
        return super.onOptionsItemSelected(item)
    }
    
    override fun onItemClick(categoryName: String?) {
        categoryName?.let {
            navigateToCategory(it)
        }
    }
    
    private fun navigateToCategory(name: String? = null) {
        CategoriesFragmentDirections.openDetailCategory(name).navigateSafe()
    }
}
