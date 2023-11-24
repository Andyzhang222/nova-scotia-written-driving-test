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
import androidx.appcompat.app.AlertDialog
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

//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null)
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            Toast.makeText(
//                baseContext,
//                "Already logged in.",
//                Toast.LENGTH_SHORT,
//            ).show()
//
//            // Navigate to main activity if user is already logged in
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
//    }

    private fun signIn(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            showAlert("Email or password cannot be empty")
            return
        }

        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success
                        Log.d(TAG, "signInWithEmail:success")
                        showAlert("Login successfully")
                    } else {
                        // If sign in fails
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        showAlert("Login failed: ${task.exception?.message}")
                    }
                }
        } catch (e: Exception) {
            Log.w(TAG, "signInWithEmail:exception", e)
            showAlert("Login failed: ${e.message}")
        }
    }

    private fun showAlert(message: String) {
        if (message == "Login successfully") {
            AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .create()
                .show()
        } else {
            AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }
}