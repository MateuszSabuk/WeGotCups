package fr.isep.wegotcups.event

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.Timestamp
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.R
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databasehandler.DatabaseHandler
import fr.isep.wegotcups.databasehandler.DownloadAndSaveImageTask
import fr.isep.wegotcups.databasehandler.EventData
import fr.isep.wegotcups.databinding.FragmentEditEventBinding
import fr.isep.wegotcups.home.EntryFragment
import java.text.SimpleDateFormat
import java.util.*

class EditEventFragment(val event: EventData) : ViewBindingFragment<FragmentEditEventBinding>() {
    private var date: String = ""
    private var time: String = ""
    private val dbh = DatabaseHandler()

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

        setImage()
        binding.nameInput.editText?.setText(event.name.toString())
        binding.description.editText?.setText(event.description.toString())
        binding.location.editText?.setText(event.location)
        binding.location.editText?.setText(event.location?.toString())
        binding.selectDate.setText(event.getTimeFormatted("dd/MMMM/yyyy"))
        binding.selectTime.setText(event.getTimeFormatted("HH:mm"))
        date = event.getTimeFormatted("MM/dd/yyyy")
        time = event.getTimeFormatted("HH:mm")
        for(i in (0 until binding.spinner.adapter.count)){
           if (event.dresscode.toString() == binding.spinner.adapter.getItem(i).toString()){
               binding.spinner.setSelection(i)
               break
           }
        }


        binding.buttonDone.setOnClickListener {
            if(validateInputs()){
                dbh.updateEvent(event, ::finishEditing)
            }
        }
    }

    private fun setImage(){
        if (event.imageUri == null){
            binding.profileFragmentImage.setImageResource(R.drawable.event_cover)
        } else {
            DownloadAndSaveImageTask(activity as Context).execute(Pair(event.imageUri.toString(), binding.profileFragmentImage))
            binding.profileFragmentImage.setImageURI(dbh.localUriFromUri(event.imageUri as Uri))
        }
    }

    private fun finishEditing(){
        (activity as MainActivity).supportFragmentManager.popBackStack()
    }

    private fun validateInputs(): Boolean{
        val name = binding.nameInput.editText?.text.toString()
        if (name.isBlank() or name.isEmpty()) {
            return false
        }
        event.name = name

        var desc = binding.description.editText?.text.toString()
        if (desc == null || desc == "null"){
            desc = ""
        }
        event.description = desc


        var loc = binding.location.editText?.text.toString()
        if (loc == null || loc == "null"){
            loc = ""
        }
        event.location = loc

        if (date.isEmpty()){
            return false
        }
        if (time.isEmpty()){
            time = "0:0"
        }
        event.datetime = Timestamp(Date("$date $time"))

        //TODO validate location
        event.dresscode = binding.spinner.selectedItem.toString()
        return true
    }


    private fun openDatePicker(){
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select time")
                .build()

        datePicker.show(childFragmentManager, "TAG")
        datePicker.addOnPositiveButtonClickListener {
            val simpleDateFormat = SimpleDateFormat("dd/MMMM/yyyy", Locale.getDefault())
            val displayDate = simpleDateFormat.format(Date(it).time)

            val dateFormat = SimpleDateFormat("MM/dd/yyyy",Locale.getDefault())
            date = dateFormat.format(Date(it).time)

            binding.selectDate.setText(displayDate)
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

            time = "$hour:$minutes"
            binding.selectTime.setText(time)
        }
    }

}