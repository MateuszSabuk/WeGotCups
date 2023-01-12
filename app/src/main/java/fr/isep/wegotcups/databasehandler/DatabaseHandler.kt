package fr.isep.wegotcups.databasehandler

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.isep.wegotcups.MainActivity
import java.util.*

class DatabaseHandler {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    fun addEvent(eventData: EventData?){
        val event = hashMapOf(
            "name" to eventData?.name.toString(),
            "owner" to auth.currentUser?.uid,
            "datetime" to eventData?.datetime,
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

    fun getMyEvents(funForEveryEvent: (EventData) -> Unit, afterEventsLoaded: () -> Unit) {
        val currentUserReference: DocumentReference = db.document("/users/${auth.currentUser?.uid}")
        db.collection("events")
            .whereEqualTo("owner", auth.currentUser?.uid)
            .get()
            .addOnSuccessListener { myDocuments ->
                db.collection("events")
                    .whereArrayContains("sharedWith", currentUserReference)
                    .get()
                    .addOnSuccessListener { sharedDocuments ->
                        var documents = (myDocuments + sharedDocuments).distinct()
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
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    // Event ID
    // User ID
    fun shareEventWithUser(eid: String, uid:String){
        if(uid == auth.currentUser?.uid.toString()){
            Log.w(TAG, "Don't share with self!")
            return
        }
        val docRef: DocumentReference = db.document("/users/$uid")
        docRef.get()
            .addOnSuccessListener { user ->
                if (user.exists()){
                    db.collection("events").document(eid)
                        .update("sharedWith", FieldValue.arrayUnion(docRef))
                        .addOnSuccessListener {
                            Log.d(TAG, "Friend successfully added!")
                            sendNotificationToUser(2, uid)
                        }
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                } else {
                    Log.w(TAG, "User does not exist!")
                }
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error looking for the document", e) }
    }


    fun addFriend(uid: String){
        val docRef: DocumentReference = db.document("/users/$uid")
        docRef.get()
            .addOnSuccessListener { user ->
                if (user.exists()){
                    db.collection("users").document(auth.currentUser?.uid.toString())
                        .update("friends", FieldValue.arrayUnion(docRef))
                        .addOnSuccessListener {
                            Log.d(TAG, "Friend successfully added!")
                            sendNotificationToUser(1, uid)
                        }
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                } else {
                    Log.w(TAG, "User does not exist!")
                }
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error looking for the document", e) }

    }

    fun sendNotificationToUser(type: Int, uid:String, from:String = auth.currentUser?.uid.toString()) {
        val notification = hashMapOf(
            "from" to from,
            "to" to uid,
            "type" to type,
            "time" to Date(),
        )
        db.collection("notifications")
            .add(notification)
            .addOnSuccessListener {
                Log.d(TAG, "Notification sent!")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error sending notification", e) }
    }

    fun getMyFriends(funForEveryFriend: (User) -> Unit, afterDataLoaded: () -> Unit){
        db.collection("users").document(auth.currentUser?.uid.toString())
            .get()
            .addOnSuccessListener { user ->
                val friends = user?.data?.get("friends") as ArrayList<DocumentReference>
                for (friend in friends){
                    friend.get().addOnSuccessListener{ friend ->
                        funForEveryFriend(User(friend))
                        afterDataLoaded()
                    }
                    .addOnFailureListener{exception ->
                        Log.w(TAG, "Error getting documents: ", exception)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun createUser(user: FirebaseUser) {
        val data = hashMapOf(
            "name" to user.displayName.toString(),
            "friends" to arrayListOf<DocumentReference>(),
            "email" to user.email.toString(),
            "avatarLocal" to true,
            "numOfAvatar" to (0..7).random()
        )
        db.collection("users").document(user.uid).set(data, SetOptions.merge())
            .addOnSuccessListener { sendNotificationToUser(0, user.uid) }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun getUser(funReference: (User) -> Unit, uid: String = auth.currentUser?.uid.toString()) {
        db.collection("users").document(uid)
            .get()
            .addOnSuccessListener { user ->
                funReference(User(user))
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun updateUser(user: User){
        if (user.id != auth.currentUser?.uid || user.id == null) return
        db.collection("users").document(user.id.toString())
            .update(user.toHashMap())
            .addOnSuccessListener {
                Log.d(TAG, "Updated user!")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun addPhoto(uri: Uri, folder: String, returnFun: (Uri) -> Unit) {
        val fileName = UUID.randomUUID().toString() +".jpg"

        val refStorage = FirebaseStorage.getInstance().reference.child("$folder/$fileName")

        refStorage.putFile(uri)
            .addOnSuccessListener(
                OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        returnFun(it)
                    }
                })
            .addOnFailureListener(OnFailureListener { e ->
                print(e.message)
            })
    }

    fun localUriFromUri(uri: Uri): Uri {
        val names = uri.toString().split('/').last().split('?').first().split("%2F")
        return Uri.parse("/data/data/fr.isep.wegotcups/files/${names?.get(0) as String}/${names?.get(1) as String}")
    }
}