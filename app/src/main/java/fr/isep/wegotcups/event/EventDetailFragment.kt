package fr.isep.wegotcups.event

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.view.*
import android.widget.Button
import android.widget.TextView
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
import fr.isep.wegotcups.event.adapter.MembersRecyclerViewAdapter
import fr.isep.wegotcups.event.adapter.MembersViewModel
import fr.isep.wegotcups.friends.AddFriendsRecyclerViewAdapter
import fr.isep.wegotcups.friends.FriendsItemViewModel
import fr.isep.wegotcups.home.EntryFragment
import java.util.*


class ModalBottomSheetPerson(var event: EventData, var par: EventDetailFragment) : BottomSheetDialogFragment() {

    private lateinit var recyclerViews : RecyclerView
    private val dbh: DatabaseHandler = DatabaseHandler()
    private var data = ArrayList<FriendsItemViewModel>()
    var filtered = ArrayList<FriendsItemViewModel>()
    var searchFilter: String = ""

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

class ModalBottomSheetPlaylist(var event: EventData, var par: EventDetailFragment) : BottomSheetDialogFragment() {
    private lateinit var doneButton : Button
    private lateinit var cancelButton : Button
    private lateinit var url: TextInputEditText
    private lateinit var more : TextView
    private val dbh: DatabaseHandler = DatabaseHandler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_spotify_add_section, container, false)

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        url = view.findViewById(R.id.playlist_url_edit)
        doneButton = view.findViewById(R.id.done_button)
        doneButton.setOnClickListener(){
            if (validateInputs()){
                event.playlist = url.text.toString()
                dbh.updateEvent(event, ::done)
                this.dialog?.hide()
            }
        }

        cancelButton = view.findViewById<Button?>(R.id.cancel_button)
        cancelButton.setOnClickListener(){
            this.dialog?.hide()
        }

        more = view.findViewById(R.id.more_spotify)
        more.movementMethod = LinkMovementMethod.getInstance()
    }

    fun done(){}

    private fun validateInputs(): Boolean{
        return (url.text.toString().startsWith("https://open.spotify.com"))
    }
}

class EventDetailFragment(var event: EventData = EventData()) : ViewBindingFragment<FragmentEventDetailBinding>() {
    private val dbh = DatabaseHandler()
    private val auth = Firebase.auth
    private var spotifyUrl: String? = null
    val URL_MIN_SIZE = 8

    private var data = ArrayList<MembersViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEventDetailBinding
        get() = FragmentEventDetailBinding::inflate

    override fun setup() {
        setText()

        dbh.getEvent(::overwriteEvent, event.id!!)

        binding.addSection.setOnClickListener {
            showBottomSheetDialogFragment(SpotifyAddSectionFragment())
        }

        binding.editBasicInfo.setOnClickListener {
            loadFragment(EditEventFragment(event))
        }

        binding.deleteEvent.setOnClickListener {
            if(event.id != null){
                dbh.deleteEvent(event.id.toString(), ::eventDeleted)
            }
        }


        if (auth.currentUser?.uid != event.owner) {
            view?.findViewById<View>(R.id.add_person)?.isVisible = false
            binding.editBasicInfo.isVisible = false
            binding.addSection.isVisible = false
            binding.deleteEvent.isVisible = false
        } else {
            binding.toolBar.setOnMenuItemClickListener { it ->
                when (it.itemId) {
                    R.id.add_person -> {
                        val modalBottomSheet = ModalBottomSheetPerson(event, this)
                        modalBottomSheet.show(parentFragmentManager, ModalBottomSheetPerson.TAG)
                    }
                }
                false
            }
        }
        binding.toolBar.setNavigationOnClickListener() {
            loadFragmentFromLeft(EntryFragment())
        }

        spotifyUrl = loadSpotifyUrl()
        if (spotifyUrl?.length!! < URL_MIN_SIZE) {
            binding.playlistCard.visibility = View.GONE
        } else {
            binding.addSection.visibility = View.GONE
            binding.openSpotify.setOnClickListener() {
                val uri: Uri = Uri.parse(spotifyUrl) // missing 'http://' will cause crashed
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }

        binding.membersRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        for (i in 1..10) {
            data.add(MembersViewModel(User(), EventData()))
        }
        val adapter = MembersRecyclerViewAdapter(data) { position -> onListItemClick(position) }
        binding.membersRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        data = ArrayList()
        refreshMembersRecycler()
    }

    fun eventDeleted(){
        activity?.supportFragmentManager?.popBackStack()
    }

    fun refreshMembersRecycler(){
        dbh.getMyFriends(::addFriendToData, ::loadDataToRecyclerView)
    }

    private fun addFriendToData(user: User){
        data.add(MembersViewModel(user, event))
    }

    private fun onListItemClick(position: Int) {
        print(position)
    }

    private fun loadDataToRecyclerView(){
        data = data.filter {it.event?.sharedWith?.contains(it.user.id.toString()) != null && it.event?.sharedWith?.contains(it.user.id.toString()) == true} as ArrayList
        val adapter = MembersRecyclerViewAdapter(data) { position -> onListItemClick(position) }
        binding.membersRecyclerView.adapter = adapter
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

    private fun setOwner(user: User) {
        if(user.name != null){
            binding.owner.text = user.name!!
        }
    }

    private fun overwriteEvent(newEvent: EventData){
        event = newEvent
        setImage()
        setText()
        dbh.getUser(::setOwner,event.owner.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }


    //TODO - make loading of data from the db
    private fun loadSpotifyUrl():String?{
        return event.playlist
    }
}