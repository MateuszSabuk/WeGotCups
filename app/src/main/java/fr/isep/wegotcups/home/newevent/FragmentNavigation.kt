package fr.isep.wegotcups.home.newevent

import androidx.fragment.app.Fragment
import fr.isep.wegotcups.R

abstract class FragmentNavigation : Fragment(){

    fun loadFragment(fragment: Fragment){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.main_fragment_container,fragment)
        transaction.commit()
    }
}