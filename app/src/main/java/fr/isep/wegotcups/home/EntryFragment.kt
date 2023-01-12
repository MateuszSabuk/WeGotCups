package fr.isep.wegotcups.home

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databasehandler.DatabaseHandler
import fr.isep.wegotcups.databinding.FragmentEntryBinding
import fr.isep.wegotcups.databasehandler.EventData
import fr.isep.wegotcups.event.EventDetailFragment
import fr.isep.wegotcups.home.newevent.NameEventFragment
import java.util.*
import kotlin.collections.ArrayList

class EntryFragment : ViewBindingFragment<FragmentEntryBinding>() {
    private var data = ArrayList<EventItemViewModel>()
    private val dbh = DatabaseHandler()
    private lateinit var adapter: EventsRecyclerViewAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEntryBinding
        get() = FragmentEntryBinding::inflate

    override fun setup() {
        updateUsername()
        (activity as MainActivity).userUpdateFunctions.add(::updateUsername)

        binding.eventRecyclerView.layoutManager = LinearLayoutManager(context)

        binding.newEvent.setOnClickListener(){
            loadFragment(NameEventFragment())
        }

        binding.showOldSwitch.setOnClickListener {
            loadRecyclerAdapter()
        }

        //TODO make loading smoother

        dbh.getMyEvents(::addEventToData, ::loadRecyclerAdapter)
    }

    private fun onListItemClick(position: Int) {
        loadFragment(EventDetailFragment(data[position].event))
    }

    private fun addEventToData(ed: EventData) {
        data.add(EventItemViewModel(ed))
    }

    private fun loadRecyclerAdapter() {
        var sorted = data.sortedWith(
            compareBy { it.event.datetime }
        )
        var filtered = sorted
        if (!binding.showOldSwitch.isChecked){
            filtered = filtered.filter { it.event.datetime > Timestamp(Date()) }
        }
        adapter = EventsRecyclerViewAdapter(filtered) { position -> onListItemClick(position) }
        binding.eventRecyclerView.adapter = adapter
    }

    private fun updateUsername(){
        binding.helloUsername.text = (activity as MainActivity).user.name.toString()
    }

}
