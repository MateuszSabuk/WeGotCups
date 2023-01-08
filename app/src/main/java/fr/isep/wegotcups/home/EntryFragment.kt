package fr.isep.wegotcups.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.R
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentEntryBinding
import fr.isep.wegotcups.event.EventDetailFragment
import fr.isep.wegotcups.home.newevent.NameEventFragment

class EntryFragment : ViewBindingFragment<FragmentEntryBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEntryBinding
        get() = FragmentEntryBinding::inflate

    override fun setup() {
        binding.helloUsername.text = (activity as MainActivity).user?.displayName.toString()

        binding.newEvent.setOnClickListener(){
            loadFragment(NameEventFragment())
        }

        binding.eventRecyclerView.layoutManager = LinearLayoutManager(context)
        val data = ArrayList<EventItemViewModel>()
        for (i in 1..20) {
            data.add(EventItemViewModel(R.drawable.event_cover, "EventData " + i, "12.10.2022 12:37"))
        }
        val adapter = EventsRecyclerViewAdapter(data) { position -> onListItemClick(position) }
        binding.eventRecyclerView.adapter = adapter
    }

    private fun onListItemClick(position: Int) {
        loadFragment(EventDetailFragment())
    }
}