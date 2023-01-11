package fr.isep.wegotcups

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isep.wegotcups.databasehandler.DatabaseHandler
import fr.isep.wegotcups.databinding.ActivityFriendsBinding
import fr.isep.wegotcups.event.EventData
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
        dbh.getMyFriends(::addFriendToData, ::loadDataToRecyclerView)
        //dbh.addFriend(UserID)
        dbh.shareEventWithUser("W1mk3Z51yZq56j9oi4qj", "Kp2JJKspZMeBcPOfwKZwnxNJV5C2")
    }
    //TODO addFriends activity
    // TODO recycler view for friends
    fun addFriendToData(name: String){
        Log.w(ContentValues.TAG, "friend: $name")
        data.add(FriendsItemViewModel(getRandomAvatar(), name, "@username"))
    }

    private fun onListItemClick(position: Int) {
        print(position)
    }

    private fun loadDataToRecyclerView(){
        print("Hello world")
        print(data)
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