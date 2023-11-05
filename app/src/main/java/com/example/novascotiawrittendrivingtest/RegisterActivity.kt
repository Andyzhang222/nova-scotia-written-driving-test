package com.example.novascotiawrittendrivingtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var creditCardEditText: EditText
    private lateinit var registerButton: Button

    private lateinit var auth: FirebaseAuth


//    public override fun onStart() {
//        super.onStart()
//        // 检查用户是否已登录（非空），并相应更新用户界面
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            reload()
//        }
//    }

    private fun reload() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.reload()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // 跳转到login
                updateUI(user)
            } else {
                // 重新加载没有达到预期效果
                val error = task.exception?.message
                Toast.makeText(this, "Failed to reload user data: $error", Toast.LENGTH_LONG).show()
            }
        }
    }


    // 更新界面的方法，参数是 FirebaseUser 类型的 currentUser (跳转到login)
    private fun updateUI(currentUser: FirebaseUser?) {
        // 检查当前用户是否为非空
        if (currentUser != null) {
            // 用户已登陆的话跳转到homePage
            // 跳转到homePageActivity (暂时写的是MainActivity)
            val homeIntent = Intent(this, MainActivity::class.java).apply {
                // 传递User到loginActivity(传递变量名：USER)
                putExtra("USER", currentUser)
            }

            startActivity(homeIntent)
            finish()
        } else {
            // 用户为 null，注册失败或者未注册
            Toast.makeText(this, "Registration failed, please try again", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth;


        // Initialize your EditTexts and Button
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        creditCardEditText = findViewById(R.id.rePasswordEditText)
        registerButton = findViewById(R.id.registerButton)

        // Register the button listener
        registerButton.setOnClickListener {
            performRegistration()
        }


    }


    private fun performRegistration() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val rePassword = creditCardEditText.text.toString()

        // Perform validation checks
        if (!validateEmailNullEmpty(email) || !validateEmail(email)) {
            showToast("Invalid Email")
            return
        }

        if (!validatePwdNullEmpty(password) || !validatePwdLength(password) || !validatePwdFormat(password)) {
            showToast("Invalid Password")
            return
        }

        if (password != rePassword) {
            showToast("Passwords do not match for the previous one")
            return
        }


        //如果验证全部通过，执行注册流程
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    showToast("Registration Successful")
                    val user = auth.currentUser

                    /*
                    //注册跳转到login
                    val LoginIntent = Intent(this, LoginActivity::class.java).apply {
                        // 传递USER到LoginActivity(传递变量名：USER)
                        putExtra("USER", user)
                    }
                    startActivity(LoginIntent)
                    finish()
                    */
                } else {
                    // If sign in fails, display a message to the user.
                    showToast("Authentication failed: ${task.exception?.message}")
                    updateUI(null)
                }
            }


    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
     * @return true if the password is between 8 -13, false if less than 8 or more than 13
     */
    fun validatePwdLength(pwd: String): Boolean {
        return pwd.length in 8..13
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
                pwd.matches(".*[0-9].*".toRegex()) &&
                pwd.matches(".*[-+_!@#$%^&*.,?].*".toRegex())
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


