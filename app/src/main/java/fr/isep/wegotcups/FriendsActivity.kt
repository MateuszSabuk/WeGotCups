package fr.isep.wegotcups

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isep.wegotcups.databasehandler.DatabaseHandler
import fr.isep.wegotcups.databinding.ActivityFriendsBinding
import fr.isep.wegotcups.databasehandler.EventData
import fr.isep.wegotcups.databasehandler.User
import fr.isep.wegotcups.friends.FriendsItemViewModel
import fr.isep.wegotcups.friends.FriendsRecyclerViewAdapter


class FriendsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFriendsBinding
    public var newEventData: EventData = EventData()

    private val dbh: DatabaseHandler = DatabaseHandler()
    private var data = ArrayList<FriendsItemViewModel>()
    private lateinit var adapter: FriendsRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.friendRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.addFriendsButton.setOnClickListener {
            startActivity(Intent(this, AddFriendActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        data = ArrayList()
        dbh.getMyFriends(::addFriendToData, ::loadDataToRecyclerView)
    }

    private fun addFriendToData(user: User){
        var userTag = ""
        if (user.userTag.toString() != "null"){
            userTag = user.userTag.toString()
        }
        data.add(FriendsItemViewModel(user))
    }

    private fun onListItemClick(position: Int) {
        print(position)
    }

    private fun loadDataToRecyclerView(){
        adapter = FriendsRecyclerViewAdapter(data) { position -> onListItemClick(position) }
        binding.friendRecyclerView.adapter = adapter
    }

    private fun getRandomAvatar(): Int {
        val rnds = (0..4).random()
        when(rnds){
            0 -> return R.drawable.avatar_deer
            1 -> return R.drawable.avatar_cat
            2 -> return R.drawable.avatar_panda
            3 -> return R.drawable.avatar_pig
            else -> return R.drawable.avatar_monkey
        }
    }
}