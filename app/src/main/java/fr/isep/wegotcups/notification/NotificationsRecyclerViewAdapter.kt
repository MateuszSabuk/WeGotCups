package fr.isep.wegotcups.notification

import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.isep.wegotcups.R
import fr.isep.wegotcups.databasehandler.DatabaseHandler
import fr.isep.wegotcups.databasehandler.DownloadAndSaveImageTask
import java.text.SimpleDateFormat
import java.util.*

class NotificationsRecyclerViewAdapter (private val mList: List<NotificationItemViewModel>, private val onItemClicked: (position: Int) -> Unit) : RecyclerView.Adapter<NotificationsRecyclerViewAdapter.ViewHolder>(){
    val dbh = DatabaseHandler()
    lateinit var parent: ViewGroup

    override fun onCreateViewHolder(_parent: ViewGroup, viewType: Int): NotificationsRecyclerViewAdapter.ViewHolder {
        parent = _parent
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_notification_view, parent, false)

        return ViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: NotificationsRecyclerViewAdapter.ViewHolder, position: Int) {
        val notification = mList[position].notification
        notification.getLetter(holder.textLetter)
        notification.getDescription(holder.textName)

        val displayDateFormat = SimpleDateFormat("dd/MMMM/yyyy HH:mm", Locale.getDefault())
        val dateAndTime = displayDateFormat.format(Date(notification.time.seconds * 1000).time).toString()
        holder.textDateAndTime.text = dateAndTime
        if(notification.seen){
            holder.notificationCardBackground.background.alpha = 120
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View, private val onItemClicked: (position: Int) -> Unit)
        : RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        val textLetter: TextView = itemView.findViewById(R.id.notification_letter)
        val textName: TextView = itemView.findViewById(R.id.task_name)
        val textDateAndTime: TextView = itemView.findViewById(R.id.event_name)
        val notificationCardBackground: CardView = itemView.findViewById(R.id.notification_card_background)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            notificationCardBackground.background.alpha = 120
            val position = bindingAdapterPosition
            onItemClicked(position)
        }
    }
}