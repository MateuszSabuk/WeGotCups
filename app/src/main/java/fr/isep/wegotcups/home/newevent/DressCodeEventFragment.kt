package fr.isep.wegotcups.home.newevent

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.children
import androidx.core.view.get
import com.google.android.material.checkbox.MaterialCheckBox
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentDressCodeEventBinding

class DressCodeEventFragment : ViewBindingFragment<FragmentDressCodeEventBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDressCodeEventBinding
            = FragmentDressCodeEventBinding::inflate

    override fun setup() {
        binding.nextButton.setOnClickListener {
            for (it in binding.radioGroup.children){
                if ((it as RadioButton).isChecked){
                    (activity as MainActivity).newEventData.dresscode = (it as RadioButton).text.toString()
                }
            }
            //TODO Input the dress code
            //TODO validate location input
            loadFragment(DescriptionEventFragment())
        }

        binding.backButton.setOnClickListener(){
            loadFragmentFromLeft(CoverPhotoEventFragment())
        }

        binding.skipButton.setOnClickListener(){
            loadFragment(DescriptionEventFragment())
        }
    }
}