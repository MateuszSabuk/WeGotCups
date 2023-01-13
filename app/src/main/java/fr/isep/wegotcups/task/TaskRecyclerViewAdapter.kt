package fr.isep.wegotcups.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isep.wegotcups.R

class TaskRecyclerViewAdapter (private val mList: List<TaskItemViewModel>, private val onItemClicked: (position: Int) -> Unit) : RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_task_view, parent, false)

        return ViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: TaskRecyclerViewAdapter.ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.taskName.text = ItemsViewModel.taskName
        holder.eventName.text = ItemsViewModel.eventName
        holder.progress.text = ItemsViewModel.progress
        holder.taskDate.text = ItemsViewModel.dateAndTime
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View, private val onItemClicked: (position: Int) -> Unit)
        : RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        val taskName: TextView = itemView.findViewById(R.id.task_name)
        val eventName: TextView = itemView.findViewById(R.id.event_name)
        val progress: TextView = itemView.findViewById(R.id.progress)
        val taskDate: TextView = itemView.findViewById(R.id.task_date)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            onItemClicked(position)
        }
    }
}