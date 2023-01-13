package fr.isep.wegotcups.event

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import fr.isep.wegotcups.ViewBindingBottomSheetFragment
import fr.isep.wegotcups.databinding.FragmentSpotifyAddSectionBinding

class SpotifyAddSectionFragment : ViewBindingBottomSheetFragment<FragmentSpotifyAddSectionBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSpotifyAddSectionBinding
        get() = FragmentSpotifyAddSectionBinding::inflate

    override fun setup() {
        binding.cancelButton.setOnClickListener(){
            this.dialog?.hide()
        }

        binding.doneButton.setOnClickListener(){
            //TODO - save data to the db
            this.dialog?.hide()
        }

        binding.moreSpotify.movementMethod = LinkMovementMethod.getInstance()
    }

}