package fr.isep.wegotcups.home.newevent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentDoneEventBinding
import fr.isep.wegotcups.databinding.FragmentLocationEventBinding
import fr.isep.wegotcups.event.EventData
import fr.isep.wegotcups.home.EntryFragment


class DoneEventFragment : ViewBindingFragment<FragmentDoneEventBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDoneEventBinding
            = FragmentDoneEventBinding::inflate

    override fun setup() {
        //Send and reset event data stored in main activity
        (activity as MainActivity).newEventData.sendToDatabase()
        (activity as MainActivity).newEventData = EventData()

        binding.nextButton.setOnClickListener {

            loadFragment(EntryFragment())
        }
    }
}
