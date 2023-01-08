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
        binding.nextButton.setOnClickListener {
            //TODO validate name input

            Log.v("the child is: ", activity.toString());
            (activity as MainActivity).newEventData.name = binding.nameInput.editText?.text.toString()
            loadFragment(DateEventFragment())
        }

        binding.cancelButton.setOnClickListener{
            //TODO
            loadFragmentFromLeft(EntryFragment())
        }
    }
}