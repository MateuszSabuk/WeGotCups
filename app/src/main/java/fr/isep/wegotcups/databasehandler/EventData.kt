package fr.isep.wegotcups.databasehandler

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.text.SimpleDateFormat
import java.util.*

class EventData {
    constructor()

    constructor(document: DocumentSnapshot?){
        id = document?.id.toString()
        name = document?.data?.get("name").toString()
        owner = document?.data?.get("owner").toString()
        val dt = document?.data?.get("datetime")
        if (dt != null){
            datetime =  dt as Timestamp
        }
        imageUri = (document?.data?.get("imageUri").toString()).toUri()
        if (imageUri == "null".toUri()) imageUri = null
        dresscode = document?.data?.get("dresscode").toString()
        description = document?.data?.get("description").toString()
        location = document?.data?.get("location").toString()
        val sw = document?.data?.get("sharedWith")
        if (sw != null){
            sharedWith = sw as ArrayList<String>
        }
    }

    var id: String? = null
    var name: String? = null
    var owner: String? = null
    var datetime: Timestamp = Timestamp(Date(0))
    var location: String = ""
    var imageUri: Uri? = null
    var dresscode: String? = null
    var description: String = ""
    var sharedWith: ArrayList<String>? = null

    fun toHashMap(): MutableMap<String, Any> {
        var map = mutableMapOf<String, Any>()
        if (name != null) {
            map["name"] = name.toString()
        }
        if (datetime != null) {
            map["datetime"] = datetime
        }
        map["location"] = location.toString()
        if (dresscode != null) {
            map["dresscode"] = dresscode.toString()
        }
        if (sharedWith != null) {
            map["sharedWith"] = sharedWith as ArrayList
        }
        map["description"] = description
        return map
    }

    fun getTimeFormatted(format:String=""): String{
        val displayDateFormat = SimpleDateFormat(format,Locale.getDefault())
        return displayDateFormat.format(Date(this.datetime.seconds * 1000).time).toString()
    }
}