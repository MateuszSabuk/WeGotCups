package fr.isep.wegotcups.home.newevent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import com.google.android.material.checkbox.MaterialCheckBox
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentDressCodeEventBinding

class DressCodeEventFragment : ViewBindingFragment<FragmentDressCodeEventBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDressCodeEventBinding
            = FragmentDressCodeEventBinding::inflate

    override fun setup() {
        binding.nextButton.setOnClickListener {
            for (it in binding.checkboxContainer.children){
                if ((it as MaterialCheckBox).isChecked){
                    (activity as MainActivity).newEventData.dressCode = (it as MaterialCheckBox).text.toString()
                }
            }
            //TODO Input the dress code
            //TODO validate location input
            loadFragment(DescriptionEventFragment())
        }

        binding.backButton.setOnClickListener(){
            //TODO
            loadFragmentFromLeft(CoverPhotoEventFragment())
        }
    }
}