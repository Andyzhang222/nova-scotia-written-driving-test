package com.example.novascotiawrittendrivingtest

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.novascotiawrittendrivingtest.helper.ValidateRegister
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.Locale

class RegisterActivity : AppCompatActivity() {

    // Late-initialized properties for UI elements
    private lateinit var emailEditText: EditText
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordEditText: EditText
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var confirmPasswordLayout: TextInputLayout
    private lateinit var registerButton: Button
    private lateinit var auth: FirebaseAuth

    // Language settings
    private var currentLanguage = "en"
    private lateinit var languageButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Firebase Auth initialization
        auth = Firebase.auth

        // Navigates to the login screen when clicked
        val toLoginTextView : TextView = findViewById(R.id.toLoginTextView)
        toLoginTextView.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
        }

        // Language switching setup
        languageButton = findViewById(R.id.languageButton)
        languageButton.setOnClickListener {
            if (Locale.getDefault().language == "en") {
                switchLanguage()
                languageButton.text = getString(R.string.language_button)
            } else {
                switchLanguage()
                languageButton.text = getString(R.string.language_button)
            }
        }

        // Initialize your EditTexts and Button
        emailEditText = findViewById(R.id.emailEditText)
        emailLayout = findViewById(R.id.emailLayout)
        passwordEditText = findViewById(R.id.passwordEditText)
        passwordLayout = findViewById(R.id.passwordLayout)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout)
        registerButton = findViewById(R.id.registerButton)

        emailFocusListener()
        passwordFocusListener()
        confirmPasswordFocusListener()

        // Register the button listener
        registerButton.setOnClickListener {
            performRegistration()
        }
    }

    /**
     *  Switches the app language and recreates the activity
     */
    private fun switchLanguage() {
        val newLang = if (getUserSelectedLanguage() == "en") "zh" else "en"
        val locale = Locale(newLang)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Save the language preference
        val sharedPref = this.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(LANGUAGE_KEY, newLang)
            apply()
        }

        recreate() // Recreate the activity to apply the new language
    }

    /**
     * Retrieves the user's saved language preference
     */
    private fun getUserSelectedLanguage(): String {
        val sharedPref = this.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE)
        return sharedPref.getString(LANGUAGE_KEY, "en") ?: "en"
    }

    /**
     * Add focus listener to email edit text to show if the email is valid or not
     */
    private fun emailFocusListener() {
        emailEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val email = emailEditText.text.toString()
                if (!ValidateRegister.validateEmailNullEmpty(email)) {
                    emailLayout.helperText = "Please enter your email"
                } else if (!ValidateRegister.validateEmail(email)) {
                    emailLayout.helperText = "Invalid Email"
                } else {
                    emailLayout.helperText = null
                }
            }
        }
    }

    /**
     * Add focus listener to password edit text to show if the password is valid or not
     */
    private fun passwordFocusListener() {
        passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // Check if the password is valid
                val password = passwordEditText.text.toString()

                // Check if the password is valid
                if (!ValidateRegister.validatePwdNullEmpty(password)) {
                    passwordLayout.helperText = "Please enter your password"
                } else if (!ValidateRegister.validatePwdLength(password)) {
                    passwordLayout.helperText = "Password length should be between 8 and 20"
                } else if (!ValidateRegister.validatePwdFormat(password)) {
                    passwordLayout.helperText = "Password should contain at least one capital letter, lower case and number"
                } else {
                    passwordLayout.helperText = null
                }
            }
        }
    }

    /**
     * Add focus listener to confirm password edit text to show if the password is valid or not
     */
    private fun confirmPasswordFocusListener() {
        confirmPasswordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // Check if the password is valid
                val password = passwordEditText.text.toString()

                // Check if the password is valid
                val rePassword = confirmPasswordEditText.text.toString()
                if (!ValidateRegister.validatePwdNullEmpty(rePassword)) {
                    confirmPasswordLayout.helperText = "Please enter your password"
                } else if (password != rePassword) {
                    confirmPasswordLayout.helperText = "Passwords do not match for the previous one"
                } else {
                    confirmPasswordLayout.helperText = null
                }
            }
        }
    }

    /**
     * Performs user registration with Firebase Authentication
     */
    private fun performRegistration() {
        // Get the email and password from the EditTexts
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()

        // Check if any of the fields are empty
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("fill_all_fields")

            // Set the helper text for each field
            emailLayout.helperText = if (email.isEmpty()) getString(R.string.enter_email) else null
            passwordLayout.helperText = if (password.isEmpty()) getString(R.string.enter_password) else null
            confirmPasswordLayout.helperText = if (confirmPassword.isEmpty()) getString(R.string.enter_password) else null
            return
        }

        // Assuming you have similar string resources for emailHelperText, passwordHelperText, and confirmPasswordHelperText
        val emailHelperText = emailLayout.helperText?.toString()
        val passwordHelperText = passwordLayout.helperText?.toString()
        val confirmPasswordHelperText = confirmPasswordLayout.helperText?.toString()

        // Check if any of the helper texts are not null
        if (emailHelperText != null) {
            showAlert(emailHelperText)
            return
        }
        if (passwordHelperText != null) {
            showAlert(passwordHelperText)
            return
        }
        if (confirmPasswordHelperText != null) {
            showAlert(confirmPasswordHelperText)
            return
        }

        // Check if the user is already signed in anonymously
        val currentUser = auth.currentUser
        if (currentUser != null && currentUser.isAnonymous) {
            // User is signed in anonymously, link the new credentials
            val credential = EmailAuthProvider.getCredential(email, password)
            currentUser.linkWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Linking successful, continue with the post-registration process
                        showAlert("registration_successful")
                    } else {
                        Log.w(TAG, "linkWithCredential:failure", task.exception)
                    }
                }
        } else {
            // User is not signed in or not anonymous, proceed with normal registration
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        showAlert("registration_successful")
                    } else {
                        Log.w(TAG, "linkWithCredential:failure", task.exception)
                    }
                }
        }
    }

    /**
     * Shows an alert dialog with a message
     */
    private fun showAlert(messageKey: String) {
        val message = getString(resources.getIdentifier(messageKey, "string", packageName))

        if (messageKey == "registration_successful") {
            AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(getString(R.string.go_to_login)) { dialog, _ ->
                    dialog.dismiss()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .create()
                .show()
        } else {
            AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }


    /**
     * Constants for shared preferences keys
     */
    companion object {
        const val LANGUAGE_KEY = "SelectedLanguage"
        const val SHARED_PREFS_FILE = "AppSettingsPrefs"
    }
}


