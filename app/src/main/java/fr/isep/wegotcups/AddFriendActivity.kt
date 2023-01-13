package fr.isep.wegotcups

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.isep.wegotcups.databasehandler.DatabaseHandler
import fr.isep.wegotcups.databasehandler.User
import fr.isep.wegotcups.databinding.ActivityAddFriendsBinding
import fr.isep.wegotcups.friends.AddFriendsRecyclerViewAdapter
import fr.isep.wegotcups.friends.FriendsItemViewModel
import fr.isep.wegotcups.friends.FriendsRecyclerViewAdapter

class AddFriendActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFriendsBinding
    val data = ArrayList<FriendsItemViewModel>()
    val dbh = DatabaseHandler()
    val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityAddFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addFriendsRecyclerView.layoutManager = LinearLayoutManager(this)


        dbh.getMyNotFriends(::addUserToData,::loadDataToRecyclerView)

        for (i in 1..10) {

        }
    }

    fun onListItemClick(position: Int){

    }

    private fun addUserToData(user: User){
        var userTag = ""
        if (user.userTag.toString() != "null"){
            userTag = user.userTag.toString()
        }
        if (user.id == auth.currentUser?.uid){
            return
        }
        data.add(FriendsItemViewModel(user))
    }

    private fun loadDataToRecyclerView(){
        val adapter = AddFriendsRecyclerViewAdapter(data) { position -> onListItemClick(position) }
        binding.addFriendsRecyclerView.adapter = adapter
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