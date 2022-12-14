package fr.isep.wegotcups

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import fr.isep.wegotcups.databinding.ActivityMainBinding
import fr.isep.wegotcups.home.EntryFragment
import fr.isep.wegotcups.loginregister.LoginFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)


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
                        loadFragment(LoginFragment())
                        true
                    }
                    R.id.item_3 -> {
                        loadFragment(LoginFragment())
                        true
                    }
                    R.id.item_4 -> {
                        loadFragment(LoginFragment())
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

}