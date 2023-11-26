package com.example.novascotiawrittendrivingtest

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LoginActivityUnitTest {

    @Mock
    private lateinit var mockAuth: FirebaseAuth

    @Mock
    private lateinit var mockTask: Task<AuthResult>

    private lateinit var loginActivity: LoginActivity

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        loginActivity = LoginActivity()
        loginActivity.auth = mockAuth

        // Mock Firebase Auth behavior
        `when`(mockAuth.signInWithEmailAndPassword(anyString(), anyString())).thenReturn(mockTask)
    }

    @Test
    fun signInWithEmail_Successful() {
        // Mock successful authentication
        `when`(mockTask.isSuccessful).thenReturn(true)

        loginActivity.signInWithEmail("test@example.com", "password")

    }

    @Test
    fun signInWithEmail_Failed() {
        // Mock failed authentication
        `when`(mockTask.isSuccessful).thenReturn(false)

        loginActivity.signInWithEmail("test@example.com", "password")
    }
}