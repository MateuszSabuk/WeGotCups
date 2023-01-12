package fr.isep.wegotcups.home

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import fr.isep.wegotcups.R
import fr.isep.wegotcups.databasehandler.DatabaseHandler
import fr.isep.wegotcups.databasehandler.DownloadAndSaveImageTask

class EventsRecyclerViewAdapter(private val mList: List<EventItemViewModel>, private val onItemClicked: (position: Int) -> Unit) : RecyclerView.Adapter<EventsRecyclerViewAdapter.ViewHolder>() {
    val dbh = DatabaseHandler()

    lateinit var parent: ViewGroup

    override fun onCreateViewHolder(_parent: ViewGroup, viewType: Int): ViewHolder {
        parent = _parent
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_event_view, parent, false)

        return ViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        if (itemsViewModel.imageUri == null){
            holder.imageView.setImageResource(itemsViewModel.image)
        } else {
            DownloadAndSaveImageTask(parent.context).execute(Pair(itemsViewModel.imageUri.toString(), holder.imageView))
            holder.imageView.setImageURI(dbh.localUriFromUri(itemsViewModel.imageUri as Uri))
        }
        holder.textName.text = itemsViewModel.name
        holder.textDateAndTime.text = itemsViewModel.dateAndTime
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View, private val onItemClicked: (position: Int) -> Unit)
        : RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.event_cover_photo_image_view)
        val textName: TextView = itemView.findViewById(R.id.event_name)
        val textDateAndTime: TextView = itemView.findViewById(R.id.event_date)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            onItemClicked(position)
        }
    }

}
