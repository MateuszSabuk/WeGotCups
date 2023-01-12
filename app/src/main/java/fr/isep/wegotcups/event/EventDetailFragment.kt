package fr.isep.wegotcups.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.isep.wegotcups.R
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databasehandler.EventData
import fr.isep.wegotcups.databinding.FragmentEventDetailBinding
import fr.isep.wegotcups.friends.AddFriendsRecyclerViewAdapter
import fr.isep.wegotcups.friends.FriendsItemViewModel
import fr.isep.wegotcups.home.EntryFragment

class ModalBottomSheetPerson : BottomSheetDialogFragment() {

    private lateinit var recyclerViews : RecyclerView

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
        val data = ArrayList<FriendsItemViewModel>()
        for (i in 1..10) {
            data.add(FriendsItemViewModel(getRandomAvatar(), "User name " + i, "@username", "randomuserid"))
        }
        val adapter = AddFriendsRecyclerViewAdapter(data) { position -> onListItemClick(position) }
        recyclerViews.adapter = adapter
    }

    fun onListItemClick(position: Int){
    }

    //TODO - remove
    private fun getRandomAvatar(): Int {
        val rnds = (0..7).random()
        when(rnds){
            0 -> return R.drawable.avatar_deer
            1 -> return R.drawable.avatar_cat
            2 -> return R.drawable.avatar_panda
            3 -> return R.drawable.avatar_pig
            4 -> return R.drawable.avatar_dog
            5 -> return R.drawable.avatar_monkey
            6 -> return R.drawable.avatar_fox
            7 -> return R.drawable.avatar_chicken
            else -> return R.drawable.avatar_monkey
        }
    }
}

class EventDetailFragment(var event: EventData = EventData()) : ViewBindingFragment<FragmentEventDetailBinding>() {
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
                    val modalBottomSheet = ModalBottomSheetPerson()
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
    }
}