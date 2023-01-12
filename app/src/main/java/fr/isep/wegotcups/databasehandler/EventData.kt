package fr.isep.wegotcups.databasehandler

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.util.*

class EventData {
    constructor()

    constructor(document: QueryDocumentSnapshot?){
        id = document?.id.toString()
        name = document?.data?.get("name").toString()
        datetime = document?.data?.get("datetime") as Timestamp
    }

    var id: String? = null
    var name: String? = null
    var datetime: Timestamp = Timestamp(Date(0))
    var location: String? = null
    var imageUri: Uri? = null
    var dresscode: String? = null
    var description: String? = null

}