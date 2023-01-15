package fr.isep.wegotcups.loginregister

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.databasehandler.DatabaseHandler
import fr.isep.wegotcups.databinding.ActivityLoginregisterBinding

class LoginRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginregisterBinding
    private lateinit var auth: FirebaseAuth
    private val dbh: DatabaseHandler = DatabaseHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        // Firebase initialization
        auth = Firebase.auth

        // Auto Login
        if (auth.currentUser != null) {
            if (auth.currentUser!!.isEmailVerified) {
                updateUI(auth.currentUser)
            }
        }

        binding = ActivityLoginregisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun createAccount(email: String, name: String, password: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")

                    val user = auth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }
                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener {
                            if (task.isSuccessful) {
                                Log.d(TAG, "Username added")
                                dbh.createUser(user)
                                sendEmailVerification()
                            }
                        }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        // [END create_user_with_email]
    }

    fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        if (email.isEmpty() or password.isEmpty()){
            Toast.makeText(baseContext, "Authentication failed",
                Toast.LENGTH_SHORT).show()
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    if(! user!!.isEmailVerified){
                        Toast.makeText(baseContext, "Email not verified!",
                            Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        // [END sign_in_with_email]
    }

    private fun sendEmailVerification() {
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) {
                Log.i("email verification","sent")
            }
        // [END send_email_verification]
    }

    private fun updateUI(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java).apply {
            // you can add values(if any) to pass to the next class or avoid using `.apply`
            putExtra("keyIdentifier", user)
        })
        finish()
    }



    companion object {
        private const val TAG = "EmailPassword"
    }
}