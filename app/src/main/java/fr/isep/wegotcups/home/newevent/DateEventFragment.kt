package fr.isep.wegotcups.home.newevent

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.Timestamp
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentDateEventBinding
import fr.isep.wegotcups.databinding.FragmentNameEvenBinding
import java.text.SimpleDateFormat
import java.util.*

class DateEventFragment : ViewBindingFragment<FragmentDateEventBinding>() {
    private var date: String = ""
    private var time: String = ""

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDateEventBinding
            = FragmentDateEventBinding::inflate

    override fun setup() {
        val event = (activity as MainActivity).newEventData

        binding.selectDate.setOnClickListener{
            openDatePicker()
        }

        binding.selectTime.setOnClickListener(){
            openTimePicker()
        }

        binding.nextButton.setOnClickListener(){
            if (date.isEmpty()){
                return@setOnClickListener
            }
            if (time.isEmpty()){
                time = "0:0"
            }
            Log.d(TAG,"$date $time")
            event.datetime = Timestamp(Date("$date $time"))

            loadFragment(LocationEventFragment())
        }

        binding.backButton.setOnClickListener(){
            loadFragmentFromLeft(NameEventFragment())
        }
    }

    private fun openDatePicker(){
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select time")
                .build()

        datePicker.show(childFragmentManager, "TAG")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormat = SimpleDateFormat("MM/dd/yyyy",Locale.getDefault())
            date = dateFormat.format(Date(it).time)

            val displayDateFormat = SimpleDateFormat("dd/MMMM/yyyy",Locale.getDefault())
            val displayDate = displayDateFormat.format(Date(it).time)

            binding.date.hint = displayDate
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

            time = "$hour:$minutes"
            binding.time.hint = time
        }
    }
}

