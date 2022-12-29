package fr.isep.wegotcups.event

import android.view.LayoutInflater
import android.view.ViewGroup
import fr.isep.wegotcups.ViewBindingBottomSheetFragment
import fr.isep.wegotcups.databinding.FragmentMenuAddSectionBinding

class MenuAddSectionFragment: ViewBindingBottomSheetFragment<FragmentMenuAddSectionBinding>(){
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMenuAddSectionBinding
        get() = FragmentMenuAddSectionBinding::inflate

    override fun setup() {
        //TODO
        print("Todo")
    }

}