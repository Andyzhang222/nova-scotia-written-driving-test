package com.example.novascotiawrittendrivingtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordEditText: EditText
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var confirmPasswordLayout: TextInputLayout
    private lateinit var registerButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth;

        val toLoginTextView : TextView = findViewById(R.id.toLoginTextView)

        toLoginTextView.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
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

    private fun emailFocusListener() {
        emailEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val email = emailEditText.text.toString()
                if (!validateEmailNullEmpty(email)) {
                    emailLayout.helperText = "Please enter your email"
                } else if (!validateEmail(email)) {
                    emailLayout.helperText = "Invalid Email"
                } else {
                    emailLayout.helperText = null
                }
            }
        }
    }

    private fun passwordFocusListener() {
        passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val password = passwordEditText.text.toString()
                if (!validatePwdNullEmpty(password)) {
                    passwordLayout.helperText = "Please enter your password"
                } else if (!validatePwdLength(password)) {
                    passwordLayout.helperText = "Password length should be between 8 and 20"
                } else if (!validatePwdFormat(password)) {
                    passwordLayout.helperText = "Password should contain at least one capital letter, lower case and number"
                } else {
                    passwordLayout.helperText = null
                }
            }
        }
    }

    private fun confirmPasswordFocusListener() {
        confirmPasswordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val password = passwordEditText.text.toString()
                val rePassword = confirmPasswordEditText.text.toString()
                if (!validatePwdNullEmpty(rePassword)) {
                    confirmPasswordLayout.helperText = "Please enter your password"
                } else if (password != rePassword) {
                    confirmPasswordLayout.helperText = "Passwords do not match for the previous one"
                } else {
                    confirmPasswordLayout.helperText = null
                }
            }
        }
    }

    private fun performRegistration() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val emailHelperText = emailLayout.helperText.toString()
        val passwordHelperText = passwordLayout.helperText.toString()
        val confirmPasswordHelperText = confirmPasswordLayout.helperText.toString()

        if (email == "" || password == "" || confirmPasswordEditText.text.toString() == "") {
            showAlert("Please fill in all the fields")
            if (email == "") {
                emailLayout.helperText = "Please enter your email"
            }

            if (password == "") {
                passwordLayout.helperText = "Please enter your password"
            }

            if (confirmPasswordEditText.text.toString() == "") {
                confirmPasswordLayout.helperText = "Please enter your password"
            }

            return
        }

        // Perform validation checks
        if (emailHelperText != "null") {
            showAlert(emailHelperText)
            return
        }

        if (passwordHelperText != "null") {
            showAlert(passwordHelperText)
            return
        }

        if (confirmPasswordHelperText != "null") {
            showAlert(confirmPasswordHelperText)
            return
        }


        // Register the user with email and password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    showAlert("Registration Successful")
                    val user = auth.currentUser

                    showAlert("Registration Successful")

                } else {
                    // If sign in fails, display a message to the user.
                    showAlert("Authentication failed: ${task.exception?.message}")
                }
            }


    }

    private fun showAlert(message: String) {
        if (message == "Registration Successful") {
            AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("Go to Login") { dialog, _ ->
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
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }



    /**
     * These methods check if the register password is validated or not
     * @param pwd the password that read from register format
     * @return true if the password contains at least one capital letter, one lower case, one symbol,
     *         and the length is less than 13 and more than 8
     */
    fun validatePwdNullEmpty(pwd: String?): Boolean {
        return !pwd.isNullOrEmpty()
    }

    /**
     * This method checks if the register password has the right length
     * @param pwd the password that read from register format
     * @return true if the password is between 8 -20, false if less than 8 or more than 20
     */
    fun validatePwdLength(pwd: String): Boolean {
        return pwd.length in 8..20
    }

    /**
     * This method checks if the register password's format
     * @param pwd the password that read from register format
     * @return true if the password is following the right format (match the format: has capital and lower letters at the same time, also with numbers and symbols)
     * false if any of the requirements are missing
     */
    fun validatePwdFormat(pwd: String): Boolean {
        return pwd.matches(".*[A-Z].*".toRegex()) &&
                pwd.matches(".*[a-z].*".toRegex()) &&
                pwd.matches(".*[0-9].*".toRegex())
    }

    /**
     * This method checks if the register password's format
     * @param pwd the password that read from register format
     * @return true if the password is following the right format (matches the format at the same time: has capital and lower letters at the same time, also with numbers and symbols)
     * else return false
     */
    fun validatePwd(pwd: String): Boolean {
        val correct = validatePwdLength(pwd) && validatePwdFormat(pwd) && validatePwdNullEmpty(pwd)
        return correct && pwd.matches("^([A-Z]+|[a-z]+|[0-9]+|[-+_!@#$%^&*.,?]+){8,13}$".toRegex())
    }

    /**
     * This method checks the register email's format
     * @param email the email that read from register format
     * @return true if the email fits the format that has a @ symbol in string and a dot symbol at last
     */
    fun validateEmail(email: String): Boolean {
        val emailRegex = "^([a-zA-Z0-9]*[-_]?[.]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$"
        return email.matches(emailRegex.toRegex())
    }

    /**
     * This method checks if the register email's format is right or not
     * @param email the email that read from register format
     * @return true if the email is not null, false if the email is null
     */
    fun validateEmailNullEmpty(email: String?): Boolean {
        return !email.isNullOrEmpty()
    }


}


