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
            var text = binding.nameInput.editText?.text.toString()
            if (text == null || text == "null"){
                text = ""
            }
            (activity as MainActivity).newEventData.description = text
            loadFragment(DoneEventFragment())
        }

        binding.backButton.setOnClickListener(){
            loadFragmentFromLeft(DressCodeEventFragment())
        }
    }
}
