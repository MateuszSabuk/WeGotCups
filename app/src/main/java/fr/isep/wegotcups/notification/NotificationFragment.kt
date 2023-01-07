package fr.isep.wegotcups.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isep.wegotcups.R
import fr.isep.wegotcups.ViewBindingFragment
import fr.isep.wegotcups.databinding.FragmentNotificationBinding
import fr.isep.wegotcups.event.EventDetailFragment

class NotificationFragment : ViewBindingFragment<FragmentNotificationBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotificationBinding
        get() = FragmentNotificationBinding::inflate

    override fun setup() {
        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(context)
        val data = ArrayList<NotificationItemViewModel>()
        for (i in 1..10) {
            data.add(NotificationItemViewModel(R.drawable.event_cover, "Notification text " + i, "1h ago"))
        }
        val adapter = NotificationsRecyclerViewAdapter(data) { position -> onListItemClick(position) }
        binding.notificationRecyclerView.adapter = adapter
    }

    private fun onListItemClick(position: Int) {
        loadFragment(EventDetailFragment())
    }
}