package fr.isep.wegotcups.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isep.wegotcups.R
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databasehandler.DatabaseHandler
import fr.isep.wegotcups.databasehandler.Notification
import fr.isep.wegotcups.databinding.FragmentNotificationBinding
import fr.isep.wegotcups.event.EventDetailFragment
import fr.isep.wegotcups.friends.FriendsItemViewModel
import fr.isep.wegotcups.friends.FriendsRecyclerViewAdapter

class NotificationFragment : ViewBindingFragment<FragmentNotificationBinding>() {

    private val dbh: DatabaseHandler = DatabaseHandler()
    private var data = ArrayList<NotificationItemViewModel>()
    private lateinit var adapter: NotificationsRecyclerViewAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotificationBinding
        get() = FragmentNotificationBinding::inflate

    override fun setup() {
        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(context)

    }

    override fun onResume() {
        super.onResume()
        data = ArrayList()
        dbh.getNotifications(::addNotificationToData,::loadDataToRecyclerView)
    }

    private fun addNotificationToData(notification: Notification){
        data.add(NotificationItemViewModel(notification))
        data.sortWith(
            compareByDescending { it.notification.time }
        )
    }

    private fun loadDataToRecyclerView(){
        adapter = NotificationsRecyclerViewAdapter(data) { position -> onListItemClick(position) }
        binding.notificationRecyclerView.adapter = adapter
    }

    private fun onListItemClick(position: Int) {
        //loadFragment(EventDetailFragment())
    }
}