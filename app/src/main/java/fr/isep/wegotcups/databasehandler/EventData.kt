package fr.isep.wegotcups.databasehandler

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.util.*

class EventData {
    fun getLocalUri(): Uri? {
        val names = imageUri?.toString()?.split('/')?.last()?.split('?')?.first()?.split("%2F")
        return Uri.parse("/data/data/fr.isep.wegotcups/files/${names?.get(0) as String}/${names?.get(1) as String}")
    }

    constructor()

    constructor(document: QueryDocumentSnapshot?){
        id = document?.id.toString()
        name = document?.data?.get("name").toString()
        datetime = document?.data?.get("datetime") as Timestamp
        imageUri = Uri.parse(document?.data?.get("imageUri").toString())
        dresscode = document?.data?.get("dresscode").toString()
        description = document?.data?.get("description").toString()
    }

    var id: String? = null
    var name: String? = null
    var datetime: Timestamp = Timestamp(Date(0))
    var location: String? = null
    var imageUri: Uri? = null
    var dresscode: String? = null
    var description: String? = null

}