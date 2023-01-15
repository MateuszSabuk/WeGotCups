package fr.isep.wegotcups.databasehandler

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import fr.isep.wegotcups.R
import java.util.*
import kotlin.collections.HashMap

class Notification {
    private val dbh = DatabaseHandler()
    constructor()

    constructor(document: DocumentSnapshot){
        try {
            id = document?.id.toString()
            from = document?.data?.get("from").toString()
            to = document?.data?.get("to").toString()

            val ty = document?.data?.get("type")
            if (ty is Int ) {
                type = ty
            } else if (ty is Long) {
                type = ty.toInt()
            }

            time = document?.data?.get("time") as Timestamp

            val eid = document?.data?.get("eventId")
            if (eid != null){
                eventId = eid as String
            }

            val s = document?.data?.get("seen")
            seen = !(s == null || s == false)

        } catch (e: Exception) {
            Log.e(TAG,"hello", e)
        }
    }

    constructor(type: Int, uid: String, from: String, eid: String? = null){
        this.type = type
        to = uid
        this.from = from
        this.eventId = eid
    }

    var id: String? = null
    var from: String? = null
    var to: String? = null
    var type: Int = -1
    var time = Timestamp(Date())
    var event: EventData? = null
    var eventId: String? = null
    var seen = false

    fun toHashMap(): HashMap<String, Any?>? {
        val notification = hashMapOf(
            "from" to from,
            "to" to to,
            "type" to type,
            "time" to time,
            "eventId" to eventId,
            "seen" to seen
        )
        for ((key, value) in notification){
            if(value == null && key != "eventId") {
                return null
            }
        }
        return notification as HashMap<String, Any?>?
    }

    fun getDescription(textView: TextView) {
        dbh.setNotificationText(textView, this)
    }

    fun getLetter(textView: TextView) {
        dbh.setLetter(textView, this)
    }

    fun markAsSeen(){
        seen = true
        dbh.updateNotification(this)
    }
}