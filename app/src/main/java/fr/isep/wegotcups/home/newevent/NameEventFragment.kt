package fr.isep.wegotcups.home.newevent

import android.view.LayoutInflater
import android.view.ViewGroup
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentNameEvenBinding

class NameEventFragment: ViewBindingFragment<FragmentNameEvenBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNameEvenBinding
            = FragmentNameEvenBinding::inflate

    override fun setup() {
        binding.nextButton.setOnClickListener {
            loadFragment(DateEventFragment())
        }
    }
}