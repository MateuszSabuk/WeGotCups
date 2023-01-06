package fr.isep.wegotcups.home.newevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentLocationEventBinding
import fr.isep.wegotcups.databinding.FragmentNameEvenBinding

class LocationEventFragment : ViewBindingFragment<FragmentLocationEventBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLocationEventBinding
            = FragmentLocationEventBinding::inflate

    override fun setup() {
        //TODO add location input
        binding.nextButton.setOnClickListener {
            //TODO validate location input
            (activity as MainActivity).newEventData.location = "here"
            loadFragment(CoverPhotoEventFragment())
        }
    }
}
