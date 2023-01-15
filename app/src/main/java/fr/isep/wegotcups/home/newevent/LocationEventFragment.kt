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
        binding.nextButton.setOnClickListener {
            var loc = binding.nameInput.editText?.text.toString()
            if (loc == null || loc == "null"){
                loc = ""
            }
            (activity as MainActivity).newEventData.location = loc
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
