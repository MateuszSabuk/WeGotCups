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
            val il = document?.data?.get("imageLocal")
            if(il != null){
                imageLocal = il as Boolean
            }

            val noi = document?.data?.get("numOfImage")
            if (noi is Int){
                numOfImage = noi
            }else if (noi is Long) {
                numOfImage = noi.toInt()
            }

            val eid = document?.data?.get("eventId")
            if (eid != null){
                eventId = eid as String
            }
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
    var imageLocal: Boolean = true
    var numOfImage: Int = 0
    var event: EventData? = null
    var eventId: String? = null

    fun getProfilePicture(imageView: ImageView) {
        if (imageLocal){
            val resource = when (numOfImage) {
                0 -> R.drawable.avatar_fox
                1 -> R.drawable.avatar_cat
                2 -> R.drawable.avatar_deer
                3 -> R.drawable.avatar_dog
                4 -> R.drawable.avatar_chicken
                5 -> R.drawable.avatar_pig
                6 -> R.drawable.avatar_monkey
                7 -> R.drawable.avatar_panda
                else -> null
            }
            if (resource != null){
                imageView.setImageResource(resource)
            }
            return
        }

    }

    fun toHashMap(): HashMap<String, Any?>? {
        val notification = hashMapOf(
            "from" to from,
            "to" to to,
            "type" to type,
            "time" to time,
            "imageLocal" to imageLocal,
            "numOfImage" to numOfImage,
            "eventId" to eventId,
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
}