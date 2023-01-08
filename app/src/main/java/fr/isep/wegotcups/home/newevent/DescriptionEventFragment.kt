package fr.isep.wegotcups.home.newevent

import android.view.LayoutInflater
import android.view.ViewGroup
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentDescriptionEventBinding

class DescriptionEventFragment : ViewBindingFragment<FragmentDescriptionEventBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDescriptionEventBinding
            = FragmentDescriptionEventBinding::inflate

    override fun setup() {
        binding.nextButton.setOnClickListener {
            //TODO validate Description input
            (activity as MainActivity).newEventData.description = binding.nameInput.editText?.text.toString()
            loadFragment(DoneEventFragment())
        }

        binding.backButton.setOnClickListener(){
            loadFragmentFromLeft(DressCodeEventFragment())
        }
    }
}
