package fr.isep.wegotcups.loginregister

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.R
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databasehandler.DatabaseHandler
import fr.isep.wegotcups.databasehandler.User
import fr.isep.wegotcups.databinding.FragmentAvatarBinding
import fr.isep.wegotcups.home.newevent.DressCodeEventFragment
import fr.isep.wegotcups.profile.ProfileFragment


class AvatarFragment : ViewBindingFragment<FragmentAvatarBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAvatarBinding
        get() = FragmentAvatarBinding::inflate
    lateinit var user: User
    val dbh = DatabaseHandler()
    var chosenLocalAvatar: Int? = null

    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun setup() {
        user = (activity as MainActivity).user
        chosenLocalAvatar = user.numOfAvatar
        user.getProfilePicture(binding.avatarFragmentImage)

        binding.avatarFox.setOnClickListener{
            chosenLocalAvatar = 0
            binding.avatarFragmentImage.setImageResource(R.drawable.avatar_fox)
        }
        binding.avatarCat.setOnClickListener{
            chosenLocalAvatar = 1
            binding.avatarFragmentImage.setImageResource(R.drawable.avatar_cat)
        }
        binding.avatarDeer.setOnClickListener{
            chosenLocalAvatar = 2
            binding.avatarFragmentImage.setImageResource(R.drawable.avatar_deer)
        }
        binding.avatarDog.setOnClickListener{
            chosenLocalAvatar = 3
            binding.avatarFragmentImage.setImageResource(R.drawable.avatar_dog)
        }
        binding.avatarChicken.setOnClickListener{
            chosenLocalAvatar = 4
            binding.avatarFragmentImage.setImageResource(R.drawable.avatar_chicken)
        }
        binding.avatarPig.setOnClickListener{
            chosenLocalAvatar = 5
            binding.avatarFragmentImage.setImageResource(R.drawable.avatar_pig)
        }
        binding.avatarMonkey.setOnClickListener{
            chosenLocalAvatar = 6
            binding.avatarFragmentImage.setImageResource(R.drawable.avatar_monkey)
        }
        binding.avatarPanda.setOnClickListener{
            chosenLocalAvatar = 7
            binding.avatarFragmentImage.setImageResource(R.drawable.avatar_panda)
        }
        binding.uploadButton.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
        binding.doneButton.setOnClickListener {
            if (chosenLocalAvatar == null){
                user.avatarLocal = false
                // TODO send image to database
            } else {
                user.numOfAvatar = chosenLocalAvatar as Int
            }
            dbh.updateUser(user)
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.avatarFragmentImage.setImageURI(imageUri)
            chosenLocalAvatar = null
        }
    }

}