package fr.isep.wegotcups.home

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
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
    private var horizontalData = ArrayList<EventItemViewModel>()
    private var horizontalFiltered = listOf<EventItemViewModel>()
    private val dbh = DatabaseHandler()
    private lateinit var user: User
    private lateinit var adapter: EventsRecyclerViewAdapter
    private lateinit var adapterHorizontal: EventsHorizontalRecyclerViewAdapter
    private var loading = true
    private var loadingHorizontal = true

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEntryBinding
        get() = FragmentEntryBinding::inflate

    override fun setup() {
        data = ArrayList()
        horizontalData = ArrayList()

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
        binding.noEventsHorizontalView.isVisible = false
        binding.noEventsView.isVisible = false

        loading = true
        loadingHorizontal = true
        dbh.getMyEvents(::addEventToData, ::loadRecyclerAdapter)
        dbh.getMyEvents(::addEventToHorizontalData, ::loadHorizontalRecyclerAdapter,1)
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

    private fun onHorizontalListItemClick(position: Int) {
        if (binding.showOldSwitch.isChecked) {
            loadFragment(EventDetailFragment(horizontalFiltered[position].event))
        }else{
            loadFragment(EventDetailFragment(horizontalData[position].event))
        }
    }

    private fun addEventToData(ed: EventData) {
        data.add(EventItemViewModel(ed))
        data.sortWith(
            compareBy { it.event.datetime }
        )
        filtered = data.filter { it.event.datetime > Timestamp(Date()) }
    }

    private fun addEventToHorizontalData(ed: EventData) {
        horizontalData.add(EventItemViewModel(ed))
        horizontalData.sortWith(
            compareBy { it.event.datetime }
        )
        horizontalFiltered = horizontalData.filter { it.event.datetime > Timestamp(Date()) }
    }

    private fun loadRecyclerAdapter() {
        loading = false
        if(!loading && !loadingHorizontal){
            binding.loadingPanel.isVisible = false
        }
        val hideOld = binding.showOldSwitch.isChecked
        adapter = if (binding.showOldSwitch.isChecked) {
            EventsRecyclerViewAdapter(filtered) { position -> onListItemClick(position) }
        }else{
            EventsRecyclerViewAdapter(data) { position -> onListItemClick(position) }
        }
        binding.eventRecyclerView.adapter = adapter

        if (!((hideOld && filtered.isEmpty()) || (!hideOld && data.size == 0))){
            binding.myEventsRecyclerView.isVisible = true
            binding.noEventsView.isVisible = false
        } else {
            binding.myEventsRecyclerView.isVisible = false
            binding.noEventsView.isVisible = true
        }
    }

    private fun loadHorizontalRecyclerAdapter() {
        loadingHorizontal = false
        if(!loading && !loadingHorizontal){
            binding.loadingPanel.isVisible = false
        }
        val hideOld = binding.showOldSwitch.isChecked
        adapterHorizontal = if (hideOld) {
            EventsHorizontalRecyclerViewAdapter(horizontalFiltered) { position -> onHorizontalListItemClick(position) }
        }else{
            EventsHorizontalRecyclerViewAdapter(horizontalData) { position -> onHorizontalListItemClick(position) }
        }
        binding.myEventsRecyclerView.adapter = adapterHorizontal

        if (!((hideOld && horizontalFiltered.isEmpty()) || (!hideOld && horizontalData.size == 0))){
            binding.myEventsRecyclerView.isVisible = true
            binding.noEventsHorizontalView.isVisible = false
        } else {
            binding.myEventsRecyclerView.isVisible = false
            binding.noEventsHorizontalView.isVisible = true
        }
    }

    private fun updateUsernameAndAvatar(){
        binding.helloUsername.text = (activity as MainActivity).user.name.toString()
        (activity as MainActivity).user.getProfilePicture(binding.imageView)
    }

}
