package fr.isep.wegotcups.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isep.wegotcups.R
import fr.isep.wegotcups.databinding.FragmentEntryBinding

class EntryFragment : Fragment() {

    private var _binding: FragmentEntryBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEntryBinding.inflate(inflater, container, false)
        return binding.root
   }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newEvent.setOnClickListener(){
            // TODO - @Patrik button not working
            loadFragment(ViewPagerFragment())
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container,fragment)
        transaction.commit()
    }
}