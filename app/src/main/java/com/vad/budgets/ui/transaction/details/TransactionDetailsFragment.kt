package com.vad.budgets.ui.transaction.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.vad.budgets.R
import com.vad.budgets.databinding.FragmentTransactionDetailsBinding
import com.vad.budgets.ui.common.BaseFragment
import com.vad.budgets.ui.transaction.details.TransactionDetailsViewModel.Companion.DEFAULT_NEW_ID
import javax.inject.Inject

class TransactionDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: TransactionDetailsViewModel

    private lateinit var binding: FragmentTransactionDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentTransactionDetailsBinding.inflate(inflater, container, false).apply {
        binding = this
        viewModel = this@TransactionDetailsFragment.viewModel
        lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val id = TransactionDetailsFragmentArgs.fromBundle(it).id
            val titleId = if (id == DEFAULT_NEW_ID) {
                viewModel.resetValues()
                R.string.title_add_transaction
            } else {
                viewModel.getExistTransaction(id)
                R.string.title_edit_transaction
            }
            actionBarController?.setTitle(titleId)
        }
        setHasOptionsMenu(true)
        viewModel.status.observe(viewLifecycleOwner) {
            if (it.isFinished) actionBarController?.onBackPressed()
        }
        binding.selectDateButton.setOnClickListener {
            TransactionDetailsFragmentDirections.openDatePicker(
                viewModel.timeInMilliSeconds.value?.toString() ?: System.currentTimeMillis().toString()
            ).navigateSafe()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_transaction_details, menu)
        viewModel.transaction.observe(viewLifecycleOwner) {
            menu.findItem(R.id.action_delete).isVisible = it != null
        }
        viewModel.status.observe(viewLifecycleOwner) {
            menu.findItem(R.id.action_delete).isEnabled = !it.isLoading
            menu.findItem(R.id.action_save).isEnabled = !it.isLoading
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {
            viewModel.saveTransaction()
        } else if (item.itemId == R.id.action_delete) {
            viewModel.deleteTransaction()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        actionBarController?.updateNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        actionBarController?.onNavigationIconClicked {
            actionBarController?.onBackPressed()
        }
    }
}
