package fr.isep.wegotcups.event

import android.view.LayoutInflater
import android.view.ViewGroup
import fr.isep.wegotcups.ViewBindingBottomSheetFragment
import fr.isep.wegotcups.databinding.FragmentSpotifyAddSectionBinding

class SpotifyAddSectionFragment : ViewBindingBottomSheetFragment<FragmentSpotifyAddSectionBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSpotifyAddSectionBinding
        get() = FragmentSpotifyAddSectionBinding::inflate

    override fun setup() {
        //TODO
        println("Todo")
    }

}