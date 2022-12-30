package fr.isep.wegotcups.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isep.wegotcups.R
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentEntryBinding
import fr.isep.wegotcups.event.EventDetailFragment

class EntryFragment : ViewBindingFragment<FragmentEntryBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEntryBinding
        get() = FragmentEntryBinding::inflate

    override fun setup() {
        binding.newEvent.setOnClickListener(){
            loadFragment(ViewPagerFragment())
        }

        binding.eventRecyclerView.layoutManager = LinearLayoutManager(context)
        val data = ArrayList<EventItemViewModel>()
        for (i in 1..20) {
            data.add(EventItemViewModel(R.drawable.event_cover, "Event " + i, "12.10.2022 12:37"))
        }
        val adapter = CustomAdapter(data) { position -> onListItemClick(position) }
        binding.eventRecyclerView.adapter = adapter
    }

    private fun onListItemClick(position: Int) {
        loadFragment(EventDetailFragment())
    }
}