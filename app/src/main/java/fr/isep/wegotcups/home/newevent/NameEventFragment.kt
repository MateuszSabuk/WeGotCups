package fr.isep.wegotcups.home.newevent

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentNameEvenBinding
import fr.isep.wegotcups.home.EntryFragment

class NameEventFragment: ViewBindingFragment<FragmentNameEvenBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNameEvenBinding
            = FragmentNameEvenBinding::inflate

    override fun setup() {
        val event = (activity as MainActivity).newEventData

        binding.nextButton.setOnClickListener {
            val text = binding.nameInput.editText?.text.toString()
            if (text.isNotBlank() and text.isNotEmpty()) {
                event.name = text
                loadFragment(DateEventFragment())
            }
        }

        binding.cancelButton.setOnClickListener{
            loadFragmentFromLeft(EntryFragment())
        }
    }
}