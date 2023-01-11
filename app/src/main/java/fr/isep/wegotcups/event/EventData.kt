package fr.isep.wegotcups.event

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.QueryDocumentSnapshot

class EventData {
    constructor()

    constructor(document: QueryDocumentSnapshot?){
        id = document?.id.toString()
        name = document?.data?.get("name").toString()
        date = document?.data?.get("date").toString()
        time = document?.data?.get("time").toString()
        Log.d(ContentValues.TAG, document?.data.toString())
    }

    var id: String? = null
    var name: String? = null
    var date: String? = null
    var time: String? = null
    var location: String? = null
    var imageUri: Uri? = null
    var dresscode: String? = null
    var description: String? = null

}