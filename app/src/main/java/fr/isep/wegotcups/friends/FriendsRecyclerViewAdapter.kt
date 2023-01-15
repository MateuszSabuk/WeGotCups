package fr.isep.wegotcups.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isep.wegotcups.R

class FriendsRecyclerViewAdapter (private val mList: List<FriendsItemViewModel>, private val onItemClicked: (position: Int) -> Unit) : RecyclerView.Adapter<FriendsRecyclerViewAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_show_my_friends, parent, false)

        return FriendsRecyclerViewAdapter.ViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: FriendsRecyclerViewAdapter.ViewHolder, position: Int) {
        val user = mList[position].user
        user.getProfilePicture(holder.imageView)
        holder.textName.text = user.name
        var userTag = ""
        if (user.userTag.toString() != "null"){
            userTag = user.userTag.toString()
        }
        holder.userTag.text = userTag
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View, private val onItemClicked: (position: Int) -> Unit)
        : RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.event_cover_photo_image_view)
        val textName: TextView = itemView.findViewById(R.id.task_name)
        val userTag: TextView = itemView.findViewById(R.id.event_name)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            onItemClicked(position)
        }
    }
}