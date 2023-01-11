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
import fr.isep.wegotcups.databinding.ActivityMainBinding
import fr.isep.wegotcups.databasehandler.EventData
import fr.isep.wegotcups.event.EventDetailFragment
import fr.isep.wegotcups.home.EntryFragment
import fr.isep.wegotcups.loginregister.LoginRegisterActivity
import fr.isep.wegotcups.notification.NotificationFragment
import fr.isep.wegotcups.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    public var newEventData: EventData = EventData()

    private lateinit var auth: FirebaseAuth
    var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        auth = Firebase.auth
        user = auth.currentUser

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(EntryFragment())
        binding.mainBottomNavigation.setOnItemSelectedListener { item ->
                when(item.itemId) {
                    R.id.item_1 -> {
                        loadFragment(EntryFragment())
                        true
                    }
                    R.id.item_2 -> {
                        loadFragment(EventDetailFragment())
                        true
                    }
                    R.id.item_3 -> {
                        loadFragment(NotificationFragment())
                        true
                    }
                    R.id.item_4 -> {
                        loadFragment(ProfileFragment())
                        true
                    }
                    else -> false
                }
            }
    }
    
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container,fragment)
        transaction.commit()
        }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginRegisterActivity::class.java).apply {
            putExtra("doLogout", true)
        })
        finish()
    }

}