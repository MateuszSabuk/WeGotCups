package fr.isep.wegotcups

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import fr.isep.wegotcups.databinding.ActivityMainBinding

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