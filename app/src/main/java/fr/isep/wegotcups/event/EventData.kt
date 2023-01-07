package fr.isep.wegotcups.event

import android.net.Uri
import android.util.Log
import com.google.android.material.checkbox.MaterialCheckBox

class EventData {
    fun sendToDatabase() {
        Log.v("the child is: ", this.toString());
    }

    lateinit var name: String
    lateinit var date: String
    lateinit var time: String
    lateinit var location: String
    var imageUri: Uri? = null
    lateinit var dressCode: String
    lateinit var description: String



}