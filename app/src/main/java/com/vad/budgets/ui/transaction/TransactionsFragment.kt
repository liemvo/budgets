package com.vad.budgets.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vad.budgets.R
import com.vad.budgets.databinding.FragmentTransactionsBinding
import com.vad.budgets.ui.actionbar.BaseFragment
import javax.inject.Inject

class TransactionsFragment @Inject constructor(
    private val transactionsViewModel: TransactionsViewModel
) : BaseFragment() {
    
    private lateinit var binding: FragmentTransactionsBinding
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =  FragmentTransactionsBinding.inflate(inflater, container, false).apply {
        lifecycleOwner = viewLifecycleOwner
        viewModel = transactionsViewModel
    }.root
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBarController?.setTitle(R.string.title_transactions)
    }
}
