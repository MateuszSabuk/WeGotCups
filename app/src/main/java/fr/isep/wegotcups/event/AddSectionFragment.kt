package fr.isep.wegotcups.event

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.isep.wegotcups.ViewBindingBottomSheetFragment
import fr.isep.wegotcups.databinding.FragmentAddSectionListDialogBinding


class AddSectionFragment : ViewBindingBottomSheetFragment<FragmentAddSectionListDialogBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddSectionListDialogBinding
        get() = FragmentAddSectionListDialogBinding::inflate

    override fun setup() {
        binding.menu.setOnClickListener{
            showBottomSheetDialogFragment(MenuAddSectionFragment())
        }
        binding.spotify.setOnClickListener{
            showBottomSheetDialogFragment(SpotifyAddSectionFragment())
        }
        binding.voting.setOnClickListener{
            showBottomSheetDialogFragment(VotingAddSectionFragment())
        }
        binding.bring.setOnClickListener{
            showBottomSheetDialogFragment(IWillBringFragment())
        }
    }

}