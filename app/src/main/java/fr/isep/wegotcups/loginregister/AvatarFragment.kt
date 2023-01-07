package fr.isep.wegotcups.loginregister

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import fr.isep.wegotcups.R
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentAvatarBinding


class AvatarFragment : ViewBindingFragment<FragmentAvatarBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAvatarBinding
        get() = FragmentAvatarBinding::inflate

    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun setup() {
        binding.avatarFox.setOnClickListener{
            binding.avatarFragmentImage.setImageResource(R.drawable.avatar_fox)
        }
        binding.avatarCat.setOnClickListener{
            binding.avatarFragmentImage.setImageResource(R.drawable.avatar_cat)
        }
        binding.avatarDeer.setOnClickListener{
            binding.avatarFragmentImage.setImageResource(R.drawable.avatar_deer)
        }
        binding.avatarDog.setOnClickListener{
            binding.avatarFragmentImage.setImageResource(R.drawable.avatar_dog)
        }
        binding.avatarChicken.setOnClickListener{
            binding.avatarFragmentImage.setImageResource(R.drawable.avatar_chicken)
        }
        binding.avatarPig.setOnClickListener{
            binding.avatarFragmentImage.setImageResource(R.drawable.avatar_pig)
        }
        binding.avatarMonkey.setOnClickListener{
            binding.avatarFragmentImage.setImageResource(R.drawable.avatar_monkey)
        }
        binding.avatarPanda.setOnClickListener{
            binding.avatarFragmentImage.setImageResource(R.drawable.avatar_panda)
        }
        binding.uploadButton.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.avatarFragmentImage.setImageURI(imageUri)
        }
    }

}