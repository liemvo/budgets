package com.vad.budgets.ui.budget.monthyear

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vad.budgets.databinding.DialogMonthYearBinding
import com.vad.budgets.ui.common.BaseDialogFragment
import javax.inject.Inject

class MonthYearPickerFragment : BaseDialogFragment() {
    @Inject
    lateinit var monthYearViewModel: MonthYearViewModel
    private lateinit var binding: DialogMonthYearBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogMonthYearBinding.inflate(inflater, container, false).apply {
            viewModel = monthYearViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.picker.displayedValues = monthYearViewModel.data.toTypedArray()
        binding.positiveButton.setOnClickListener {
            monthYearViewModel.notifySelectedDate()
            dialog?.dismiss()
        }
        binding.negativeButton.setOnClickListener {
            dialog?.dismiss()
        }
    }
}
