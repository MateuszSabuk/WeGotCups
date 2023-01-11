package fr.isep.wegotcups.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import fr.isep.wegotcups.FriendsActivity
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.R
import fr.isep.wegotcups.databinding.FragmentProfileBinding
import fr.isep.wegotcups.loginregister.AvatarFragment


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var user: FirebaseUser

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        user = FirebaseAuth.getInstance().currentUser!!
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
        // TODO MATEUSZ add Image from database to picture
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get data from the user
        binding.profileFragmentName.text = user?.displayName.toString()
        binding.buttonLogout.setOnClickListener {
            if (activity is MainActivity) {
                (activity as MainActivity).signOut()
            }
        }
        binding.editPictureButton.setOnClickListener{
            loadFragment(AvatarFragment())
        }

        binding.myFriendsButton.setOnClickListener {
            startActivity(Intent(activity, FriendsActivity::class.java))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter_from_left,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        );
        transaction.replace(R.id.main_fragment_container,fragment)
        transaction.commit()
    }

}