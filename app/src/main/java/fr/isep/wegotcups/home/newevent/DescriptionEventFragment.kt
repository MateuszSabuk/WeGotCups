package fr.isep.wegotcups.home.newevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isep.wegotcups.databinding.FragmentDescriptionEventBinding

class DescriptionEventFragment : FragmentNavigation() {
    private var _binding: FragmentDescriptionEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDescriptionEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.nextButton.setOnClickListener(){
            loadFragment(DoneEventFragment())
        }
    }
}