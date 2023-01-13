package fr.isep.wegotcups.task

import android.view.LayoutInflater
import android.view.ViewGroup
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentAddTaskBinding

class AddTaskFragment : ViewBindingFragment<FragmentAddTaskBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddTaskBinding
        get() = FragmentAddTaskBinding::inflate

    override fun setup() {

    }
}