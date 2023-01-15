package fr.isep.wegotcups.databasehandler

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import android.view.inputmethod.InputMethodSession.EventCallback
import android.widget.TextView
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.isep.wegotcups.MainActivity
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class DatabaseHandler {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    companion object {
        lateinit var me: User
        var myFriendList = mutableSetOf<String>()
        var staticVariablesStarted: Boolean = false
    }
    init {
        if(!staticVariablesStarted){
            getUser()
            staticVariablesStarted = true
        }
    }
    private fun initMe (user: User){
        me = user
    }


    fun addEvent(eventData: EventData?){
        val event = hashMapOf(
            "name" to eventData?.name.toString(),
            "owner" to auth.currentUser?.uid,
            "datetime" to eventData?.datetime,
            "location" to eventData?.location.toString(),
            "imageUri" to eventData?.imageUri.toString(),
            "dresscode" to eventData?.dresscode.toString(),
            "description" to eventData?.description.toString(),
        )

        db.collection("events")
            .add(event)
            .addOnSuccessListener { Log.d(TAG, "Event successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun getMyEvents(funForEveryEvent: (EventData) -> Unit, afterEventsLoaded: () -> Unit, sharedWithMe:Int = 0) {
        when (sharedWithMe){
            1 -> {
                db.collection("events")
                    .whereArrayContains("sharedWith", auth.currentUser?.uid.toString())
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
                    }}
            2 -> {
                db.collection("events")
                    .whereEqualTo("owner", auth.currentUser?.uid)
                    .get()
                    .addOnSuccessListener { myDocuments ->
                        db.collection("events")
                            .whereArrayContains("sharedWith", auth.currentUser?.uid.toString())
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
            else -> {
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
    }
    fun getEvent(eventReturn: (EventData)-> Unit, eid: String){
        db.collection("events")
            .document(eid)
            .get()
            .addOnSuccessListener { event ->
                eventReturn(EventData(event))
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun updateEvent(event: EventData, done: ()->Unit) {
        db.collection("events").document(event.id!!)
            .update(event.toHashMap())
            .addOnSuccessListener {
                Log.d(TAG, "Event successfully updated!")
                done()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
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
                        .update("sharedWith", FieldValue.arrayUnion(uid))
                        .addOnSuccessListener {
                            Log.d(TAG, "Event successfully shared!")
                            sendNotificationToUser(2, uid, eid = eid)
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
                            getUser(::initMe)
                        }
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                } else {
                    Log.w(TAG, "User does not exist!")
                }
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error looking for the document", e) }
    }

    fun sendNotificationToUser(type: Int, uid:String, from:String = auth.currentUser?.uid.toString(), eid: String? = null) {
        var notification: Notification = if(eid != null){
            Notification(type, uid, from)
        }else {
            Notification(type, uid, from, eid)
        }
        var map: HashMap<String, Any?>? = notification.toHashMap() ?: return@sendNotificationToUser
        db.collection("notifications")
            .add(map as HashMap<String, Any?>)
            .addOnSuccessListener {
                Log.d(TAG, "Notification sent!")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error sending notification", e) }
    }

    fun getNotifications(funForEveryNotification: (Notification) -> Unit, endFunction: () -> Unit){
        db.collection("notifications")
            .whereEqualTo("to", auth.uid)
            .get()
            .addOnSuccessListener { notifications ->
                for ( notification in notifications ){
                    funForEveryNotification(Notification(notification))
                }
                endFunction()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun getMyFriends(funForEveryUser: (User) -> Unit, afterDataLoaded: () -> Unit, invert: Boolean = false){
        var isFriend:Boolean
        db.collection("users")
            .get()
            .addOnSuccessListener { users ->
                if (me.friends != null){
                    for (user in users) {
                        isFriend = false
                        for (friend in (me.friends as ArrayList)) {
                            if (user.id == friend.id){
                                isFriend = true
                            }
                        }
                        Log.d(TAG, user.data["name"].toString() + " " + isFriend)
                        if ((isFriend && !invert)||(!isFriend && invert)){
                            funForEveryUser(User(user))
                        }
                    }
                }
                afterDataLoaded()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun getAllUsers(funForEveryUser: (User) -> Unit, afterDataLoaded: () -> Unit){
        db.collection("users")
            .get()
            .addOnSuccessListener { users ->
                for (u in users){
                    funForEveryUser(User(u))
                }
                afterDataLoaded()
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
            .addOnSuccessListener {
                sendNotificationToUser(0, user.uid)
                getUser()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun getUser(funReference: (User) -> Unit = ::initMe, uid: String = auth.currentUser?.uid.toString()) {
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
                getUser()
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

    fun tagExists(newTag: String, validated: (Boolean, String)-> Unit) {
        db.collection("users")
            .get()
            .addOnSuccessListener { users ->
                for (user in users){
                    if (user.data?.get("userTag") == newTag && user.id != auth.currentUser?.uid ) {
                        validated(false, newTag)
                        return@addOnSuccessListener
                    }
                }
                validated(true, newTag)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

    }

    fun setNotificationText(textView: TextView, notification: Notification) {
        when (notification.type){
            0 -> textView.text = "Welcome!"
            1 -> getNotificationAddedFriend(textView, notification.from.toString())
            2 -> getNotificationSharedEvent(textView, notification.eventId.toString())
            else -> ""
            //    0 -> "Welcome!"
            //    1 -> "$from has added you to the friend list!"
            //    2 -> "You have been added to a new event: ${event.toString()}"
        }
    }

    private fun getNotificationAddedFriend(textView: TextView, uid: String) {
        db.collection("users").document(uid)
            .get()
            .addOnSuccessListener { user ->
                textView.text = "${User(user).name.toString()} has added you to the friend list!"
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    private fun getNotificationSharedEvent(textView: TextView, eid: String) {
        db.collection("events").document(eid)
            .get()
            .addOnSuccessListener { event ->
                Log.d(TAG,EventData(event).name.toString())
                textView.text = "You have been added to a new event: ${EventData(event).name.toString()}"
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }



}