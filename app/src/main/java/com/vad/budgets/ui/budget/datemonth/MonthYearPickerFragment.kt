package com.vad.budgets.ui.budget.datemonth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vad.budgets.databinding.DialogMonthYearBinding
import com.vad.budgets.ui.common.BaseDialogFragment

class MonthYearPickerFragment : BaseDialogFragment() {
    private lateinit var binding: DialogMonthYearBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogMonthYearBinding.inflate(inflater, container, false)
        return binding.root
    }
}
