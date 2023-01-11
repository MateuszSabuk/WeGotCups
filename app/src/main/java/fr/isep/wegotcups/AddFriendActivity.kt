package fr.isep.wegotcups

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isep.wegotcups.databinding.ActivityAddFriendsBinding
import fr.isep.wegotcups.friends.AddFriendsRecyclerViewAdapter
import fr.isep.wegotcups.friends.FriendsItemViewModel

class AddFriendActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFriendsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityAddFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addFriendsRecyclerView.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<FriendsItemViewModel>()
        for (i in 1..10) {
            data.add(FriendsItemViewModel(getRandomAvatar(), "User name " + i, "@username", "randomuserid"))
        }
        val adapter = AddFriendsRecyclerViewAdapter(data) { position -> onListItemClick(position) }
        binding.addFriendsRecyclerView.adapter = adapter
    }

    fun onListItemClick(position: Int){

    }

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