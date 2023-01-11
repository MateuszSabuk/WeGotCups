package fr.isep.wegotcups

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.isep.wegotcups.databasehandler.DatabaseHandler
import fr.isep.wegotcups.databinding.ActivityFriendsBinding
import fr.isep.wegotcups.databinding.ActivityMainBinding
import fr.isep.wegotcups.event.EventData
import fr.isep.wegotcups.event.EventDetailFragment
import fr.isep.wegotcups.home.EntryFragment
import fr.isep.wegotcups.loginregister.LoginRegisterActivity
import fr.isep.wegotcups.notification.NotificationFragment

class FriendsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFriendsBinding
    public var newEventData: EventData = EventData()

    private val dbh: DatabaseHandler = DatabaseHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbh.getMyFriends(::temp)
        //dbh.addFriend(UserID)
        dbh.shareEventWithUser("W1mk3Z51yZq56j9oi4qj", "Kp2JJKspZMeBcPOfwKZwnxNJV5C2")
    }
    //TODO addFriends activity
    // TODO recycler view for friends
    fun temp(name: String){
        binding.tempText.text = "${binding.tempText.text} $name"
        Log.w(ContentValues.TAG, "friend: $name")
    }
}