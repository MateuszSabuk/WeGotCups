package fr.isep.wegotcups

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import java.util.*
import kotlin.collections.ArrayList

class AddFriendActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFriendsBinding
    val data = ArrayList<FriendsItemViewModel>()
    var filtered = ArrayList<FriendsItemViewModel>()
    var searchFilter: String = ""
    val dbh = DatabaseHandler()
    val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityAddFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addFriendsRecyclerView.layoutManager = LinearLayoutManager(this)


        binding.searchFriends.editText?.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                searchFilter = binding.searchFriends.editText?.text.toString()
                    .lowercase(Locale.getDefault())
                loadDataToRecyclerView()
            }

        })


        dbh.getMyFriends(::addUserToData,::loadDataToRecyclerView, true)

    }

    private fun onListItemClick(position: Int){

    }

    private fun addUserToData(user: User){
        if (user.id == auth.currentUser?.uid){
            return
        }
        data.add(FriendsItemViewModel(user))
    }

    private fun loadDataToRecyclerView(){
        filtered = data.filter {
                    it.user.userTag?.lowercase()?.contains(searchFilter) == true
                    ||
                    it.user.name!!.lowercase().contains(searchFilter)} as ArrayList
        val adapter = AddFriendsRecyclerViewAdapter(filtered) { position -> onListItemClick(position) }
        binding.addFriendsRecyclerView.adapter = adapter
    }
}