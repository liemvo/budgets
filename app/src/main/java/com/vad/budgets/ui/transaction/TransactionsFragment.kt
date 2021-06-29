package com.vad.budgets.ui.transaction

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
import com.vad.budgets.databinding.FragmentTransactionsBinding
import com.vad.budgets.ui.common.ItemClickListener
import com.vad.budgets.ui.common.actionbar.BaseFragment
import javax.inject.Inject

class TransactionsFragment @Inject constructor(
    private val transactionsViewModel: TransactionsViewModel,
) : BaseFragment(), ItemClickListener {

    private lateinit var binding: FragmentTransactionsBinding

    private val adapter by lazy {
        TransactionAdapter(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentTransactionsBinding.inflate(inflater, container, false).apply {
        binding = this
        lifecycleOwner = viewLifecycleOwner
        viewModel = transactionsViewModel
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBarController?.setTitle(R.string.title_transactions)
        setHasOptionsMenu(true)
        DividerItemDecoration(nonNullContext, LinearLayoutManager.VERTICAL).run {
            binding.transactions.addItemDecoration(this)
        }
        binding.transactions.adapter = adapter
        transactionsViewModel.transactions.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add) {
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(id: Any) {
    }
}
