package com.example.novascotiawrittendrivingtest

import org.junit.runner.RunWith
import com.google.firebase.auth.FirebaseAuth
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FirebaseAuthenticatorTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authenticator: FirebaseAuthenticator

    @Before
    fun setUp() {
        // Mock FirebaseAuth
        firebaseAuth = mock()

        // Instantiate FirebaseAuthenticator with mocked FirebaseAuth
        authenticator = FirebaseAuthenticator(firebaseAuth)
    }

    @Test
    fun authenticateWithEmail_Successful() {
        val email = "test@example.com"
        val password = "password"
        val mockTask = Tasks.forResult(Mockito.mock(AuthResult::class.java))

        // Mocking the Firebase Auth behavior
        whenever(firebaseAuth.signInWithEmailAndPassword(email, password)).thenReturn(mockTask)

        // Call authenticateWithEmail
        authenticator.authenticateWithEmail(email, password) { isSuccess, _ ->
            assertTrue(isSuccess)
        }
    }

    @Test
    fun authenticateWithEmail_Failed() {
        val email = "test@example.com"
        val password = "wrongpassword"
        val mockTask = Tasks.forException<AuthResult>(Exception("Authentication failed"))

        // Mocking the Firebase Auth behavior
        whenever(firebaseAuth.signInWithEmailAndPassword(email, password)).thenReturn(mockTask)

        // Call authenticateWithEmail
        authenticator.authenticateWithEmail(email, password) { isSuccess, _ ->
            assertFalse(isSuccess)
        }
    }
}