package fr.isep.wegotcups.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.isep.wegotcups.R
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentEventDetailBinding

class ModalBottomSheetPerson : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_add_contact, container, false)

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}

class EventDetailFragment : ViewBindingFragment<FragmentEventDetailBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEventDetailBinding
        get() = FragmentEventDetailBinding::inflate


    override fun setup() {
        binding.addSection.setOnClickListener{
            showBottomSheetDialogFragment(AddSectionFragment())
        }

        binding.editBasicInfo.setOnClickListener{
            loadFragment(EditEventFragment())
        }

        binding.toolBar.setOnMenuItemClickListener{it->
            when(it.itemId){
                R.id.add_person -> {
                    val modalBottomSheet = ModalBottomSheetPerson()
                    modalBottomSheet.show(parentFragmentManager, ModalBottomSheetPerson.TAG)
                }
                R.id.add_task -> {
                    println("Task")
                }
            }
            false
        }
//        binding.topPanel.setOnCreateContextMenuListener{
//
//        }
    }
}