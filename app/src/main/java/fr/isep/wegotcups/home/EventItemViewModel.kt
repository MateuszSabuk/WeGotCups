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
    var dateAndTime = event.getTimeFormatted("dd/MMMM/yyyy HH:mm")
}
