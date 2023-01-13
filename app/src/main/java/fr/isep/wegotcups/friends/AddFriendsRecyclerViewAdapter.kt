package fr.isep.wegotcups.friends

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.R
import fr.isep.wegotcups.databasehandler.DatabaseHandler

class AddFriendsRecyclerViewAdapter (private val mList: List<FriendsItemViewModel>, private val onItemClicked: (position: Int) -> Unit) : RecyclerView.Adapter<AddFriendsRecyclerViewAdapter.ViewHolder>(){
    private var dbh = DatabaseHandler()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendsRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_add_friend_view, parent, false)

        return AddFriendsRecyclerViewAdapter.ViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: AddFriendsRecyclerViewAdapter.ViewHolder, position: Int) {
        val user = mList[position].user
        val event = mList[position].event
        user.getProfilePicture(holder.imageView)
        holder.textName.text = user.name
        var userTag = ""
        if (user.userTag.toString() != "null"){
            userTag = user.userTag.toString()
        }
        holder.userTag.text = userTag
        holder.buttonAddFriend.setOnClickListener{
            Log.d(TAG, event?.id.toString()+user.id.toString())
            if (event != null){
                dbh.shareEventWithUser(event.id.toString(),user.id.toString())
            } else {
                dbh.addFriend(user.id.toString())
            }
            println("Add user with id: " + user.id.toString())
            holder.buttonAddFriend.text = "Added"
            holder.buttonAddFriend.isEnabled = false
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View, private val onItemClicked: (position: Int) -> Unit)
        : RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.event_cover_photo_image_view)
        val textName: TextView = itemView.findViewById(R.id.event_name)
        val userTag: TextView = itemView.findViewById(R.id.event_date)
        val buttonAddFriend: Button = itemView.findViewById(R.id.add_friend)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            onItemClicked(position)
        }
    }
}