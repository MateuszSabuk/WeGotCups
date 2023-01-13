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
import fr.isep.wegotcups.databasehandler.User
import fr.isep.wegotcups.event.EventDetailFragment
import fr.isep.wegotcups.home.newevent.NameEventFragment
import java.util.*
import kotlin.collections.ArrayList

class EntryFragment : ViewBindingFragment<FragmentEntryBinding>() {
    private var data = ArrayList<EventItemViewModel>()
    private var filtered = listOf<EventItemViewModel>()
    private val dbh = DatabaseHandler()
    private lateinit var user: User
    private lateinit var adapter: EventsRecyclerViewAdapter
    private lateinit var adapterHorizontal: EventsHorizontalRecyclerViewAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEntryBinding
        get() = FragmentEntryBinding::inflate

    override fun setup() {
        data = ArrayList()

        updateUsernameAndAvatar()
        (activity as MainActivity).userUpdateFunctions.add(::updateUsernameAndAvatar)

        binding.eventRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.myEventsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.newEvent.setOnClickListener(){
            loadFragment(NameEventFragment())
        }

        binding.showOldSwitch.setOnClickListener {
            loadRecyclerAdapter()
            loadHorizontalRecyclerAdapter()
        }


        //TODO make loading smoother

        dbh.getMyEvents(::addEventToData, ::loadRecyclerAdapter, ::loadHorizontalRecyclerAdapter)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun onListItemClick(position: Int) {
        if (binding.showOldSwitch.isChecked) {
            loadFragment(EventDetailFragment(filtered[position].event))
        }else{
            loadFragment(EventDetailFragment(data[position].event))
        }
    }

    private fun addEventToData(ed: EventData) {
        data.add(EventItemViewModel(ed))
        data.sortWith(
            compareBy { it.event.datetime }
        )
        filtered = data.filter { it.event.datetime > Timestamp(Date()) }
    }

    private fun loadRecyclerAdapter() {
        adapter = if (binding.showOldSwitch.isChecked) {
            EventsRecyclerViewAdapter(filtered) { position -> onListItemClick(position) }
        }else{
            EventsRecyclerViewAdapter(data) { position -> onListItemClick(position) }
        }
        binding.eventRecyclerView.adapter = adapter
    }

    private fun loadHorizontalRecyclerAdapter() {
        adapterHorizontal = if (binding.showOldSwitch.isChecked) {
            EventsHorizontalRecyclerViewAdapter(filtered) { position -> onListItemClick(position) }
        }else{
            EventsHorizontalRecyclerViewAdapter(data) { position -> onListItemClick(position) }
        }
        binding.myEventsRecyclerView.adapter = adapterHorizontal
    }

    private fun updateUsernameAndAvatar(){
        binding.helloUsername.text = (activity as MainActivity).user.name.toString()
        (activity as MainActivity).user.getProfilePicture(binding.imageView)
    }

}
