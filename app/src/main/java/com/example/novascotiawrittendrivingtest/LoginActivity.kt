package com.example.novascotiawrittendrivingtest

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.Locale

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyLanguageSetting()

        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Initialize language change button
        val languageButton: Button = findViewById(R.id.languageButton)
        languageButton.setOnClickListener {
            switchLanguage()
        }

        // Update language button text based on the current language
        updateLanguageButtonText()

        // Initialize login button
        val loginButton: Button = findViewById(R.id.loginButton)

        // Initialize register text
        val toRegisterText: TextView = findViewById(R.id.toRegisterText)

        // Set login button listener
        loginButton.setOnClickListener {
            // Get email and password from EditText
            val emailEt: EditText = findViewById(R.id.emailEdit)
            val passwordEt: EditText = findViewById(R.id.passwordEdit)

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

    /**
     * Checks if the user is already signed in and navigates to the main activity if so
     */
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            navigateToMainActivity()
        }
    }

    /**
     * Applies the language setting based on saved user preference
     */
    private fun applyLanguageSetting() {
        val language = getUserSelectedLanguage()
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    /**
     * Switches the app language and saves the preference
     */
    private fun switchLanguage() {
        val newLang = if (getUserSelectedLanguage() == "en") "zh" else "en"
        saveLanguagePreference(newLang)
        recreate()
    }

    /**
     * Saves the user's language preference in shared preferences
     */
    private fun saveLanguagePreference(language: String) {
        val sharedPref = this.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(LANGUAGE_KEY, language)
            apply()
        }
    }

    /**
     * Retrieves the user's saved language preference
     */
    private fun getUserSelectedLanguage(): String {
        val sharedPref = this.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE)
        return sharedPref.getString(LANGUAGE_KEY, "en") ?: "en"
    }

    /**
     * Updates the language button's text based on the current language
     */
    private fun updateLanguageButtonText() {
        val languageButton: Button = findViewById(R.id.languageButton)
        languageButton.text = if (getUserSelectedLanguage() == "en") getString(R.string.language_button) else getString(R.string.language_button)
    }

    /**
     * Signs in the user with Firebase Authentication and handles success or failure
     */
    private fun signIn(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            showAlert(R.string.error_email_password_empty)
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    showAlert(R.string.login_success)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    showAlert(R.string.login_failed)
                }
            }
    }

    /**
     * Displays an alert dialog with a message based on the result of the sign-in process
     */
    private fun showAlert(messageResId: Int) {
        val message = getString(messageResId)
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
                if (messageResId == R.string.login_success) {
                    navigateToMainActivity()
                }
            }
            .create().show()
    }

    /**
     * Navigates to the main activity after successful sign-in
     */
    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Constants for shared preferences keys
     */
    companion object {
        const val LANGUAGE_KEY = "SelectedLanguage"
        const val SHARED_PREFS_FILE = "AppSettingsPrefs"
    }
}