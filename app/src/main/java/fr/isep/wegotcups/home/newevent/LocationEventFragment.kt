package fr.isep.wegotcups.home.newevent

import android.view.LayoutInflater
import android.view.ViewGroup
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentLocationEventBinding

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

        binding.backButton.setOnClickListener(){
            loadFragmentFromLeft(DateEventFragment())
        }

        binding.skipButton.setOnClickListener(){
            loadFragment(CoverPhotoEventFragment())
        }
    }
}
