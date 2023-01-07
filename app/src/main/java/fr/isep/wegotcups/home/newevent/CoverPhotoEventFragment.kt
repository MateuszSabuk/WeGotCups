package fr.isep.wegotcups.home.newevent

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentCoverPhotoEventBinding
import fr.isep.wegotcups.databinding.FragmentLocationEventBinding

class CoverPhotoEventFragment : ViewBindingFragment<FragmentCoverPhotoEventBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCoverPhotoEventBinding
            = FragmentCoverPhotoEventBinding::inflate

    private val pickImage = 100
    private var imageUri: Uri? = null


    override fun setup() {
        binding.imageView.setOnClickListener(){
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
        binding.nextButton.setOnClickListener {
            //TODO validate image input
            (activity as MainActivity).newEventData.imageUri = imageUri
            loadFragment(DressCodeEventFragment())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.imageView.setImageURI(imageUri)
        }
    }
}