package com.vad.budgets.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vad.budgets.R
import com.vad.budgets.ui.actionbar.BaseFragment
import javax.inject.Inject

class TransactionsFragment @Inject constructor() : BaseFragment() {
    
    private lateinit var transactionsViewModel: TransactionsViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        transactionsViewModel =
            ViewModelProvider(this).get(TransactionsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_transactions, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        transactionsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBarController?.setTitle(R.string.title_transactions)
    }
}
