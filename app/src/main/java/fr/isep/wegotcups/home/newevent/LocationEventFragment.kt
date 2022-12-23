package fr.isep.wegotcups.home.newevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isep.wegotcups.databinding.FragmentLocationEventBinding

class LocationEventFragment : FragmentNavigation() {
    private var _binding: FragmentLocationEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.nextButton.setOnClickListener(){
            loadFragment(CoverPhotoEventFragment())
        }
    }
}