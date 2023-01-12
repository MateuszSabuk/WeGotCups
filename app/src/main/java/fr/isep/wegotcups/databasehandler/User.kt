package fr.isep.wegotcups.databasehandler

import android.net.Uri
import android.widget.ImageView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import fr.isep.wegotcups.R
import java.util.ArrayList

class User {
    private val dbh = DatabaseHandler()
    private val auth = Firebase.auth

    constructor()

    constructor(document: DocumentSnapshot){
        id = document?.id.toString()
        name = document?.data?.get("name").toString()
        friends = document?.data?.get("friends") as ArrayList<DocumentReference>
        val ut = document?.data?.get("userTag")
        if (ut != null){
            userTag = ut.toString()
        }
        email = document?.data?.get("email").toString()
        val al = document?.data?.get("avatarLocal")
        if (al is Boolean){
            avatarLocal = al
        }
        val noa = document?.data?.get("numOfAvatar")
        if (noa is Int ) {
            numOfAvatar = noa
        } else if (noa is Long) {
            numOfAvatar = noa.toInt()
        }
    }

    var id: String? = null
    var name: String? = null
    var email: String? = null
    var friends: ArrayList<DocumentReference>? = null
    var userTag: String? = null
    var avatarLocal: Boolean = true
    var numOfAvatar: Int = 0
    var avatarUri: Uri? = null

    fun getProfilePicture(imageView: ImageView) {
        if (avatarLocal){
            val resource = when (numOfAvatar) {
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
        if (avatarUri == null) return

    }

    fun toHashMap(): MutableMap<String, Any> {
        var map = mutableMapOf<String, Any>()
        if (name != null) {
            map["name"] = name.toString()
        }
        if (userTag != null) {
            map["userTag"] = userTag.toString()
        }
        map["avatarLocal"] = avatarLocal
        map["numOfAvatar"] = numOfAvatar
        return map
    }
}