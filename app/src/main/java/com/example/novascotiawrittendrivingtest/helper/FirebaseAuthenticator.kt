package com.example.novascotiawrittendrivingtest.helper

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthenticator (private val firebaseAuth: FirebaseAuth) : Authenticator {
    override fun authenticateWithEmail(email: String, password: String, onComplete: (Boolean, Exception?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            onComplete(task.isSuccessful, task.exception)
        }
    }

    override fun authenticateAnonymously(onComplete: (Boolean, Exception?) -> Unit) {
        firebaseAuth.signInAnonymously().addOnCompleteListener { task ->
            onComplete(task.isSuccessful, task.exception)
        }
    }

    override fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}