package fr.isep.wegotcups.home.newevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentDescriptionEventBinding
import fr.isep.wegotcups.databinding.FragmentLocationEventBinding

class DescriptionEventFragment : ViewBindingFragment<FragmentDescriptionEventBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDescriptionEventBinding
            = FragmentDescriptionEventBinding::inflate

    override fun setup() {
        binding.nextButton.setOnClickListener {
            //TODO validate Description input
            (activity as MainActivity).newEventData.description = binding.nameInput.editText?.text.toString()
            loadFragment(DoneEventFragment())
        }
    }
}
