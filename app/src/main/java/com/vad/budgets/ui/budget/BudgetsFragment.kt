package com.vad.budgets.ui.budget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.vad.budgets.R
import com.vad.budgets.databinding.FragmentBudgetsBinding
import com.vad.budgets.ui.common.BaseFragment
import javax.inject.Inject

class BudgetsFragment @Inject constructor(
    private val budgetsViewModel: BudgetsViewModel
) : BaseFragment() {
    private lateinit var binding: FragmentBudgetsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        FragmentBudgetsBinding.inflate(inflater, container, false).apply {
            binding = this
            viewModel = budgetsViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBarController?.setTitle(R.string.title_budgets)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_budget, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_calendar) {
            BudgetsFragmentDirections.openMontyYearPicker().navigateSafe()
        }
        return super.onOptionsItemSelected(item)
    }
}