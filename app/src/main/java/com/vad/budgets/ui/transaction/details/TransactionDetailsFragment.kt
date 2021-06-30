package com.vad.budgets.ui.transaction.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vad.budgets.R
import com.vad.budgets.databinding.FragmentTransactionDetailsBinding
import com.vad.budgets.ui.common.actionbar.BaseFragment
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
                R.string.title_add_category
            } else {
                R.string.title_edit_category
            }
            actionBarController?.setTitle(titleId)
        }
    }

    override fun onResume() {
        super.onResume()
        actionBarController?.updateNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        actionBarController?.onNavigationIconClicked {
            actionBarController?.onBackPressed()
        }
    }

    companion object {
        private const val DEFAULT_NEW_ID = -1L
    }
}
