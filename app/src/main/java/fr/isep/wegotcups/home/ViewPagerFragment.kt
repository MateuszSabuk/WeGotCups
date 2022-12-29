package fr.isep.wegotcups.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isep.wegotcups.R
import fr.isep.wegotcups.databinding.FragmentNameEvenBinding
import fr.isep.wegotcups.databinding.FragmentViewPagerBinding
import fr.isep.wegotcups.home.newevent.CoverPhotoEventFragment
import fr.isep.wegotcups.home.newevent.DateEventFragment
import fr.isep.wegotcups.home.newevent.NameEventFragment

class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf<Fragment>(
            NameEventFragment(),
            DateEventFragment(),
            CoverPhotoEventFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter
    }
}