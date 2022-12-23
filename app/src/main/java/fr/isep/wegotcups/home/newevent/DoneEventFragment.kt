package fr.isep.wegotcups.home.newevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isep.wegotcups.databinding.FragmentDoneEventBinding


class DoneEventFragment : FragmentNavigation() {
    private var _binding: FragmentDoneEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoneEventBinding.inflate(inflater, container, false)
        return binding.root
    }
}