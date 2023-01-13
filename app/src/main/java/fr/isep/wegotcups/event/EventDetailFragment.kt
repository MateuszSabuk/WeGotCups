package fr.isep.wegotcups.event

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.isep.wegotcups.R
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databasehandler.DatabaseHandler
import fr.isep.wegotcups.databasehandler.EventData
import fr.isep.wegotcups.databasehandler.User
import fr.isep.wegotcups.databinding.FragmentEventDetailBinding
import fr.isep.wegotcups.friends.AddFriendsRecyclerViewAdapter
import fr.isep.wegotcups.friends.FriendsItemViewModel
import fr.isep.wegotcups.friends.FriendsRecyclerViewAdapter
import fr.isep.wegotcups.home.EntryFragment

class ModalBottomSheetPerson(var event: EventData) : BottomSheetDialogFragment() {

    private lateinit var recyclerViews : RecyclerView

    private val dbh: DatabaseHandler = DatabaseHandler()
    private var data = ArrayList<FriendsItemViewModel>()
    private lateinit var adapter: AddFriendsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_add_contact, container, false)

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViews = view.findViewById(R.id.add_members_recycler_view)
        recyclerViews.layoutManager = LinearLayoutManager(context)
    }


    override fun onResume() {
        super.onResume()
        data = ArrayList()
        dbh.getMyFriends(::addFriendToData, ::loadDataToRecyclerView)
    }

    private fun addFriendToData(user: User){
        data.add(FriendsItemViewModel(user, event))
    }

    private fun onListItemClick(position: Int) {
        print(position)
    }

    private fun loadDataToRecyclerView(){
        adapter = AddFriendsRecyclerViewAdapter(data) { position -> onListItemClick(position) }
        recyclerViews.adapter = adapter
    }
}

class EventDetailFragment(var event: EventData = EventData()) : ViewBindingFragment<FragmentEventDetailBinding>() {
    private var spotifyUrl: String? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEventDetailBinding
        get() = FragmentEventDetailBinding::inflate

    override fun setup() {
        binding.eventName.title = event.name.toString()

        binding.addSection.setOnClickListener{
            showBottomSheetDialogFragment(AddSectionFragment())
        }

        binding.editBasicInfo.setOnClickListener{
            loadFragment(EditEventFragment())
        }

        binding.toolBar.setOnMenuItemClickListener{it->
            when(it.itemId){
                R.id.add_person -> {
                    val modalBottomSheet = ModalBottomSheetPerson(event)
                    modalBottomSheet.show(parentFragmentManager, ModalBottomSheetPerson.TAG)
                }
                R.id.add_task -> {
                    println("Task")
                }
            }
            false
        }
        binding.toolBar.setNavigationOnClickListener(){
            loadFragmentFromLeft(EntryFragment())
        }

        spotifyUrl = loadSpotifyUrl()
        if(spotifyUrl.isNullOrBlank()){
           binding.playlistCard.visibility = View.GONE
        }else{
            binding.openSpotify.setOnClickListener(){
                val uri: Uri = Uri.parse(spotifyUrl) // missing 'http://' will cause crashed
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }

    }

    //TODO - make loading of data from the db
    private fun loadSpotifyUrl():String?{
        return "https://open.spotify.com/playlist/15sBiFlWRq2MuTeq4Q7Sb3?si=fSGtjKjOTR2Ox0p5Hr0_fw&pt=ffc09e7a3b7e3629324d29b48b60b911"
    }
}