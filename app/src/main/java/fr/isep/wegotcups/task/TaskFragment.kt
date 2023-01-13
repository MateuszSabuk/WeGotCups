package fr.isep.wegotcups.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentTaskBinding
import fr.isep.wegotcups.event.EventDetailFragment

class TaskFragment : ViewBindingFragment<FragmentTaskBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTaskBinding
        get() = FragmentTaskBinding::inflate

    override fun setup() {
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(context)
        val data = ArrayList<TaskItemViewModel>()
        for (i in 1..10) {
            data.add(TaskItemViewModel("Task name n." + i, "23/1/2023 22:00", "Notification text " + i,"Active"))
        }
        val adapter = TaskRecyclerViewAdapter(data) { position -> onListItemClick(position) }
        binding.taskRecyclerView.adapter = adapter
    }

    private fun onListItemClick(position: Int) {
        loadFragment(AddTaskFragment())
    }
}