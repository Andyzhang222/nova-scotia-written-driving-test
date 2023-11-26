package com.example.novascotiawrittendrivingtest

interface Authenticator {
    fun authenticateWithEmail(email: String, password: String, onComplete: (Boolean, Exception?) -> Unit)
    fun authenticateAnonymously(onComplete: (Boolean, Exception?) -> Unit)
    fun isUserSignedIn(): Boolean
}