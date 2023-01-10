package fr.isep.wegotcups

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

    private lateinit var auth: FirebaseAuth
    var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        auth = Firebase.auth
        user = auth.currentUser

        binding = ActivityFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}