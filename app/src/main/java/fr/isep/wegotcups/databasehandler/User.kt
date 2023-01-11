package fr.isep.wegotcups.databasehandler

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.util.ArrayList

class User {
    constructor()

    constructor(document: QueryDocumentSnapshot?){
        id = document?.id.toString()
        name = document?.data?.get("name").toString()
        friends = document?.data?.get("friends") as ArrayList<DocumentReference>
        userTag = document?.data?.get("userTag").toString()
    }

    var id: String? = null
    var name: String? = null
    var friends: ArrayList<DocumentReference>? = null
    var userTag: String? = null
}