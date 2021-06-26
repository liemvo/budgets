package com.vad.budgets.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vad.budgets.R
import com.vad.budgets.databinding.FragmentCategoriesBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CategoriesFragment @Inject constructor(
    private val categoriesViewModel: CategoriesViewModel
) : DaggerFragment(), CategoryInterface {
    
    
    private lateinit var adapter: CategoryAdapter
    
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
        
        setHasOptionsMenu(true)
        
        binding.categories.adapter = CategoryAdapter(this).also {
            adapter = it
        }
        
        categoriesViewModel.categories.observe(viewLifecycleOwner, {
            adapter.updateList(it)
        })
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_add, menu)
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_add) {
            navigateToCategoryDetail()
        }
        return super.onOptionsItemSelected(item)
    }
    
    override fun onItemClick(categoryName: String?) {
        navigateToCategoryDetail(categoryName)
    }
    
    private fun navigateToCategoryDetail(name: String? = null) {
        val action = CategoriesFragmentDirections.openDetailCategory(name)
        findNavController().navigate(action)
    }
}
