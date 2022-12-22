package fr.isep.wegotcups.home.newevent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import fr.isep.wegotcups.R
import fr.isep.wegotcups.databinding.FragmentNameEvenBinding

class NameEventFragment : Fragment() {
    private var _binding: FragmentNameEvenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNameEvenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.nameNext.setOnClickListener {
            findNavController().navigate(R.id.action_first_to_second)
        }
    }

}