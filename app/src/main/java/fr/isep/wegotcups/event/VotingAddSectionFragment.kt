package fr.isep.wegotcups.event

import android.view.LayoutInflater
import android.view.ViewGroup
import fr.isep.wegotcups.ViewBindingBottomSheetFragment
import fr.isep.wegotcups.databinding.FragmentVotingAddSectionBinding

class VotingAddSectionFragment : ViewBindingBottomSheetFragment<FragmentVotingAddSectionBinding>(){
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentVotingAddSectionBinding
        get() = FragmentVotingAddSectionBinding::inflate

    override fun setup() {
//        TODO("Not yet implemented")
        print("TODO")
    }

}