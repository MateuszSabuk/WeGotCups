package fr.isep.wegotcups

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.WindowCompat
import fr.isep.wegotcups.databasehandler.DatabaseHandler
import fr.isep.wegotcups.databinding.ActivityFriendsBinding
import fr.isep.wegotcups.databasehandler.EventData

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