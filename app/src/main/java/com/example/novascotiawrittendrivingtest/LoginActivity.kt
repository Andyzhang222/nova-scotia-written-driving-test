package com.example.novascotiawrittendrivingtest

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Initialize login button
        val loginButton : Button = findViewById(R.id.loginButton)

        // Initialize register text
        val toRegisterText : TextView = findViewById(R.id.toRegisterText)

        // Set login button listener
        loginButton.setOnClickListener {
            // Get email and password from EditText
            val emailEt : EditText = findViewById(R.id.emailEdit)
            val passwordEt : EditText = findViewById(R.id.passwordEdit)

            // Get email and password from EditText
            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()

            // Sign in with email and password
            signIn(email, password)
        }

        // Set register text listener
        toRegisterText.setOnClickListener {
            // Navigate to register activity
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
            finish()
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //TODO: Go to main activity
            Toast.makeText(
                baseContext,
                "Already logged in.",
                Toast.LENGTH_SHORT,
            ).show()
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext,
                        "Authentication success.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    // TODO: Go to main activity
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    // TODO: Change it to a dialog
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

}