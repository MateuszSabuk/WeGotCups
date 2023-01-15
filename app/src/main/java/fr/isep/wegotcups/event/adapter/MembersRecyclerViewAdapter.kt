package fr.isep.wegotcups.event.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isep.wegotcups.R
import fr.isep.wegotcups.databasehandler.DatabaseHandler

class MembersRecyclerViewAdapter (private val mList: List<MembersViewModel>, private val onItemClicked: (position: Int) -> Unit) : RecyclerView.Adapter<MembersRecyclerViewAdapter.ViewHolder>(){
    private var dbh = DatabaseHandler()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_profile_horizontal_view, parent, false)

        return MembersRecyclerViewAdapter.ViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: MembersRecyclerViewAdapter.ViewHolder, position: Int) {
        val user = mList[position].user
        val event = mList[position].event
        user.getProfilePicture(holder.imageView)
        holder.textName.text = user.name
        var userTag = ""
        if (user.userTag.toString() != "null"){
            userTag = user.userTag.toString()
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View, private val onItemClicked: (position: Int) -> Unit)
        : RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.image_card_view)
        val textName: TextView = itemView.findViewById(R.id.user_name)
        val userTag: TextView = itemView.findViewById(R.id.user_tag)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            onItemClicked(position)
        }
    }
}