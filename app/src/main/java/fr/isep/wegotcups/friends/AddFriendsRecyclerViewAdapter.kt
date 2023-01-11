package fr.isep.wegotcups.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isep.wegotcups.R

class AddFriendsRecyclerViewAdapter (private val mList: List<FriendsItemViewModel>, private val onItemClicked: (position: Int) -> Unit) : RecyclerView.Adapter<AddFriendsRecyclerViewAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendsRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_add_friend_view, parent, false)

        return AddFriendsRecyclerViewAdapter.ViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: AddFriendsRecyclerViewAdapter.ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.imageView.setImageResource(ItemsViewModel.image)
        holder.textName.text = ItemsViewModel.name
        holder.textDateAndTime.text = ItemsViewModel.dateAndTime
        holder.buttonAddFriend.setOnClickListener(){
            //TODO addFriend function
            println("Add user with id: " + ItemsViewModel.userId)
            holder.buttonAddFriend.text = "Friend added"
            holder.buttonAddFriend.isEnabled = false
//            Toast.makeText(getContext(), "Hello", Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View, private val onItemClicked: (position: Int) -> Unit)
        : RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.event_cover_photo_image_view)
        val textName: TextView = itemView.findViewById(R.id.event_name)
        val textDateAndTime: TextView = itemView.findViewById(R.id.event_date)
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