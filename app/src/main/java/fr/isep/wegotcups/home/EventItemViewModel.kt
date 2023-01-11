package fr.isep.wegotcups.home

import fr.isep.wegotcups.R
import fr.isep.wegotcups.databasehandler.EventData

data class EventItemViewModel(var event: EventData){
    var image = R.drawable.event_cover
    var name: String = event.name.toString()
    var dateAndTime = event.date.toString() + " " + event.time.toString()
}
