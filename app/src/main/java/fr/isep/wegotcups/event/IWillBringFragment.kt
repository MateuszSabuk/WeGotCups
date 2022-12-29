package fr.isep.wegotcups.event

import android.view.LayoutInflater
import android.view.ViewGroup
import fr.isep.wegotcups.ViewBindingBottomSheetFragment
import fr.isep.wegotcups.databinding.FragmentIWillBringBinding


class IWillBringFragment : ViewBindingBottomSheetFragment<FragmentIWillBringBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIWillBringBinding
        get() = FragmentIWillBringBinding::inflate

    override fun setup() {
//        TODO("Not yet implemented")
    }

}
