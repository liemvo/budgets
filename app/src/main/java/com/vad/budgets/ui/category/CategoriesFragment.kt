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
import com.vad.budgets.data.WorkStatus
import com.vad.budgets.databinding.FragmentCategoriesBinding
import com.vad.budgets.ui.common.ItemClickListener
import com.vad.budgets.ui.common.BaseFragment
import javax.inject.Inject

class CategoriesFragment @Inject constructor(
    private val categoriesViewModel: CategoriesViewModel
) : BaseFragment(), ItemClickListener {
    
    private val adapter by lazy {
        CategoryAdapter(this)
    }
    
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
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DividerItemDecoration(nonNullContext, LinearLayoutManager.VERTICAL).run {
            binding.categories.addItemDecoration(this)
        }
        actionBarController?.setTitle(R.string.title_categories)
        
        setHasOptionsMenu(true)
        
        binding.categories.adapter = adapter
        
        categoriesViewModel.categories.observe(viewLifecycleOwner, {
            if (it is WorkStatus.Success) {
                adapter.updateList(it.data)
            }
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

    override fun onItemClick(id: Any) {
        (id as? String)?.let {
            navigateToCategory(it)
        }
    }
    
    private fun navigateToCategory(name: String? = null) {
        CategoriesFragmentDirections.openDetailCategory(name).navigateSafe()
    }
}
