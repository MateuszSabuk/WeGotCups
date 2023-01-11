package fr.isep.wegotcups.databasehandler

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class User {
    private val dbh = DatabaseHandler()
    private val auth = Firebase.auth

    constructor()

    constructor(document: DocumentSnapshot){
        id = document?.id.toString()
        name = document?.data?.get("name").toString()
        friends = document?.data?.get("friends") as ArrayList<DocumentReference>
        userTag = document?.data?.get("userTag").toString()
        email = document?.data?.get("email").toString()
    }

    var id: String? = null
    var name: String? = null
    var email: String? = null
    var friends: ArrayList<DocumentReference>? = null
    var userTag: String? = null
}