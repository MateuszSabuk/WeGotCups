package fr.isep.wegotcups.event

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.isep.wegotcups.R
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databasehandler.DatabaseHandler
import fr.isep.wegotcups.databasehandler.DownloadAndSaveImageTask
import fr.isep.wegotcups.databasehandler.EventData
import fr.isep.wegotcups.databasehandler.User
import fr.isep.wegotcups.databinding.FragmentEventDetailBinding
import fr.isep.wegotcups.friends.AddFriendsRecyclerViewAdapter
import fr.isep.wegotcups.friends.FriendsItemViewModel
import fr.isep.wegotcups.home.EntryFragment
import fr.isep.wegotcups.task.AddTaskFragment
import java.util.*


class ModalBottomSheetPerson(var event: EventData, var par: EventDetailFragment) : BottomSheetDialogFragment() {

    private lateinit var recyclerViews : RecyclerView

    private val dbh: DatabaseHandler = DatabaseHandler()
    private var data = ArrayList<FriendsItemViewModel>()
    var filtered = ArrayList<FriendsItemViewModel>()
    var searchFilter: String = ""
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
        var searchFriends = view.findViewById<TextInputEditText>(R.id.search_friends_event)
        searchFriends?.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                searchFilter = searchFriends?.text.toString()
                    .lowercase(Locale.getDefault())
                loadDataToRecyclerView()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dbh.getEvent(::updateEvent, par.event.id.toString())
    }

    private fun updateEvent(eventData: EventData) {
        par.event = eventData
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
        filtered = data.filter {
            it.user.userTag?.lowercase()?.contains(searchFilter) == true ||
            it.user.name!!.lowercase().contains(searchFilter)
        } as ArrayList
        filtered = filtered.filter {it.event?.sharedWith?.contains(it.user.id.toString()) == null || it.event?.sharedWith?.contains(it.user.id.toString()) == false} as ArrayList
        val adapter = AddFriendsRecyclerViewAdapter(filtered) { position -> onListItemClick(position) }
        recyclerViews.adapter = adapter
    }
}

class EventDetailFragment(var event: EventData = EventData()) : ViewBindingFragment<FragmentEventDetailBinding>() {
    private val dbh = DatabaseHandler()
    private val auth = Firebase.auth
    private var spotifyUrl: String? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEventDetailBinding
        get() = FragmentEventDetailBinding::inflate

    override fun setup() {
        setText()
        dbh.getEvent(::overwriteEvent, event.id!!)

        binding.addSection.setOnClickListener{
            showBottomSheetDialogFragment(AddSectionFragment())
        }

        binding.editBasicInfo.setOnClickListener{
            loadFragment(EditEventFragment(event))
        }

        if(auth.currentUser?.uid != event.owner){
            view?.findViewById<View>(R.id.add_person)?.isVisible = false
            view?.findViewById<View>(R.id.add_task)?.isVisible = false
            binding.editBasicInfo.isVisible = false
            binding.addSection.isVisible = false
        } else {
            binding.toolBar.setOnMenuItemClickListener{it->
                when(it.itemId){
                    R.id.add_person -> {
                        val modalBottomSheet = ModalBottomSheetPerson(event,this)
                        modalBottomSheet.show(parentFragmentManager, ModalBottomSheetPerson.TAG)
                    }
                    R.id.add_task -> {
                        loadFragment(AddTaskFragment())
                    }
                }
                false
            }
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

    private fun setImage(){
        if (event.imageUri == null){
            binding.eventCoverPhotoImageView.setImageResource(R.drawable.event_cover)
        } else {
            DownloadAndSaveImageTask(activity as Context).execute(Pair(event.imageUri.toString(), binding.eventCoverPhotoImageView))
            binding.eventCoverPhotoImageView.setImageURI(dbh.localUriFromUri(event.imageUri as Uri))
        }
    }

    private fun setText() {
        binding.eventName.title = event.name.toString()
        binding.eventDetailTime.text = event.getTimeFormatted("HH:mm")
        binding.date.text = event.getTimeFormatted("dd/MM/yyyy")
        binding.description.text = event.description
        binding.location.text = event.location
        if (event.dresscode == null){
            event.dresscode = "not specified"
        }
        binding.dressCode.text = event.dresscode
    }

    private fun overwriteEvent(newEvent: EventData){
        event = newEvent
        setImage()
        setText()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

    }


    //TODO - make loading of data from the db
    private fun loadSpotifyUrl():String?{
        return "https://open.spotify.com/playlist/15sBiFlWRq2MuTeq4Q7Sb3?si=fSGtjKjOTR2Ox0p5Hr0_fw&pt=ffc09e7a3b7e3629324d29b48b60b911"
    }
}