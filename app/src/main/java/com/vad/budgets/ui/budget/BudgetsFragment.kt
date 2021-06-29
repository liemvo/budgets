package com.vad.budgets.ui.budget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vad.budgets.R
import com.vad.budgets.ui.common.actionbar.BaseFragment
import javax.inject.Inject

class BudgetsFragment @Inject constructor() : BaseFragment() {
    
    private lateinit var budgetsViewModel: BudgetsViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        budgetsViewModel =
            ViewModelProvider(this).get(BudgetsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_budgets, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        budgetsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBarController?.setTitle(R.string.title_budgets)
    }
}