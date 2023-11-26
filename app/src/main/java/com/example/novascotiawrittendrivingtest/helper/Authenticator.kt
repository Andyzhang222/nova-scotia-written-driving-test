package com.example.novascotiawrittendrivingtest.helper

interface Authenticator {
    /**
     * Authenticate with email and password
     */
    fun authenticateWithEmail(email: String, password: String, onComplete: (Boolean, Exception?) -> Unit)

    /**
     * Authenticate anonymously
     */
    fun authenticateAnonymously(onComplete: (Boolean, Exception?) -> Unit)

    /**
     * Check if user is signed in
     */
    fun isUserSignedIn(): Boolean
}