package fr.isep.wegotcups.event

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import fr.isep.wegotcups.R
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentEditEventBinding
import java.text.SimpleDateFormat
import java.util.*

class EditEventFragment : ViewBindingFragment<FragmentEditEventBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEditEventBinding
        get() = FragmentEditEventBinding::inflate

    override fun setup() {
        setHasOptionsMenu(true)

        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.dress_code_styles,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinner.adapter = adapter
            }
        }

        binding.selectTime.setOnClickListener{
            openTimePicker()
        }

        binding.selectDate.setOnClickListener{
            openDatePicker()
        }
    }


    private fun openDatePicker(){
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select time")
                .build()

        datePicker.show(childFragmentManager, "TAG")
        datePicker.addOnPositiveButtonClickListener {
            val simpleDateFormat = SimpleDateFormat("dd/MMMM/yyyy", Locale.getDefault())
            val date = simpleDateFormat.format(Date(it).time)

            binding.date.hint = date
        }

    }

    private fun openTimePicker(){
        val isSystem24hFormat = DateFormat.is24HourFormat(context)
        val clockFormat = if (isSystem24hFormat) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val timePicker =
            MaterialTimePicker.Builder()
                .setTimeFormat(clockFormat)
                .setTitleText("Select date")
                .build()

        timePicker.show(childFragmentManager, "TAG")

        timePicker.addOnPositiveButtonClickListener {
            val hour = timePicker.hour
            val minutes = timePicker.minute

            binding.time.hint = hour.toString() + ":" + minutes.toString()
        }
    }

}