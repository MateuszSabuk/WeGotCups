package fr.isep.wegotcups.databasehandler

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.isep.wegotcups.event.EventData

class DatabaseHandler {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    fun addEvent(eventData: EventData?){
        val event = hashMapOf(
            "name" to eventData?.name.toString(),
            "owner" to auth.currentUser?.uid,
            "date" to eventData?.date.toString(),
            "time" to eventData?.time.toString(),
            "location" to eventData?.location.toString(),
            //TODO set image uri
            "imageUri" to eventData?.imageUri.toString(),
            "dresscode" to eventData?.dresscode.toString(),
            "description" to eventData?.description.toString(),
        )

        db.collection("events")
            .add(event)
            .addOnSuccessListener { Log.d(TAG, "Event successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun getMyEvents(funForEveryEvent: (EventData) -> Unit,afterEventsLoaded: () -> Unit, includeSharedWithMe: Boolean = true) {
        db.collection("events")
            .whereEqualTo("owner", auth.currentUser?.uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val event = EventData(document)
                    funForEveryEvent(event)
                }
                afterEventsLoaded()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
}