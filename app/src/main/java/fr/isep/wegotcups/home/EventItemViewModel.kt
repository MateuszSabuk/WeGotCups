package fr.isep.wegotcups.home

import android.net.Uri
import fr.isep.wegotcups.R
import fr.isep.wegotcups.databasehandler.EventData
import java.text.SimpleDateFormat
import java.util.*

data class EventItemViewModel(var event: EventData){
    var image = R.drawable.event_cover
    var imageUri = event.imageUri
    var name: String = event.name.toString()
    private val displayDateFormat = SimpleDateFormat("dd/MMMM/yyyy HH:mm", Locale.getDefault())
    var dateAndTime = displayDateFormat.format(Date(event.datetime.seconds * 1000).time).toString()
}
