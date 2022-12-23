package fr.isep.wegotcups.home.newevent

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import fr.isep.wegotcups.databinding.FragmentDateEventBinding
import java.text.SimpleDateFormat
import java.util.*

class DateEventFragment : FragmentNavigation() {
    private var _binding: FragmentDateEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDateEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.selectDate.setOnClickListener{
            openDatePicker()
        }

        binding.selectTime.setOnClickListener(){
            openTimePicker()
        }

        binding.nextButton.setOnClickListener(){
            loadFragment(CoverPhotoEventFragment())
        }
    }

    private fun openDatePicker(){
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select time")
                .build()

        datePicker.show(childFragmentManager, "TAG")
        datePicker.addOnPositiveButtonClickListener {
            val simpleDateFormat = SimpleDateFormat("dd/MMMM/yyyy",Locale.getDefault())
            val date = simpleDateFormat.format(Date(it).time)

            binding.date.hint = date
        }

    }

    private fun openTimePicker(){
        val isSystem24hFormat = is24HourFormat(context)
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

