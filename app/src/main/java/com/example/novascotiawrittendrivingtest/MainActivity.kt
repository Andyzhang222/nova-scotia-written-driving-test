package com.example.novascotiawrittendrivingtest


import android.content.Context
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import android.widget.TextView
import androidx.cardview.widget.CardView
import java.util.Locale
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.appcompat.app.AlertDialog
import com.example.novascotiawrittendrivingtest.dataClass.User
import com.example.novascotiawrittendrivingtest.test.DrivingTestActivity
import com.example.novascotiawrittendrivingtest.test.EmptyWrongActivity
import com.example.novascotiawrittendrivingtest.test.WrongQuestionReviewActivity

class MainActivity : AppCompatActivity() {

    private  lateinit var practiceTestContainer : CardView
    private lateinit var questionReviewContainer: CardView
    private lateinit var aiAssistantContainer: CardView
    private lateinit var testLocation: CardView
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private var questionCount = 0
    private lateinit var navToolbar: Toolbar
    private lateinit var database: DatabaseReference
    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set language
        setLanguage()

        // Set content view
        setContentView(R.layout.activity_main)

        // Get the navigation
        practiceTestContainer = findViewById(R.id.practiceTestContainer)
        questionReviewContainer = findViewById(R.id.questionReviewContainer)
        aiAssistantContainer = findViewById(R.id.AI_assistant_container)
        testLocation=findViewById(R.id.testLocationContainer)

        // Get the progress bar
        progressText = findViewById(R.id.progressText)

        // Initialize Firebase Auth to check if user is logged in
        val user = Firebase.auth.currentUser

        // If user is not logged in, navigate to login activity, else get user id
        if (user == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            user.let {
                userId = it.uid
            }
        }

        // Initialize toolbar
        initialToolBar()

        // Set navigation listeners
        setNavigationListener(user)

        // Set question
        database = Firebase.database.reference
        val incorrectQuestionsRef = database.child("users").child(userId).child("incorrectQuestions")
        incorrectQuestionsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Data exists
                    questionReviewContainer.setOnClickListener {
                        // Navigate to question review activity
                        val questionReviewIntent = Intent(this@MainActivity, WrongQuestionReviewActivity::class.java)
                        startActivity(questionReviewIntent)
                        finish()
                    }
                } else {
                    // Data is null or the path doesn't exist
                    questionReviewContainer.setOnClickListener {
                        // Navigate to question review activity
                        val questionReviewIntent = Intent(this@MainActivity, EmptyWrongActivity::class.java)
                        startActivity(questionReviewIntent)
                        finish()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(ContentValues.TAG, "Database error: $databaseError")
            }
        })

        // Set progress bar
        progress()

        // Notification implementation
        createNotificationChannel()

        // Schedule notifications
        val notificationManager =
            com.example.novascotiawrittendrivingtest.notification.NotificationManager(this)
        notificationManager.scheduleNotification()
    }

    private fun setLanguage(languageCode: String? = null) {
        val sharedPref = getSharedPreferences("AppSettingsPrefs", MODE_PRIVATE)
        val currentLanguage = languageCode ?: sharedPref.getString("SelectedLanguage", Locale.getDefault().language)

        val locale = Locale(currentLanguage)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Save the new language preference if it's provided
        if (languageCode != null) {
            with(sharedPref.edit()) {
                putString("SelectedLanguage", languageCode)
                apply()
            }
        }
    }

    /**
     * Set the language to the opposite of the current language
     */
    private fun setNavigationListener(user : com.google.firebase.auth.FirebaseUser?) {

        aiAssistantContainer.setOnClickListener {
            if  (user!!.isAnonymous) {
                showAlert(R.string.register_alert)
            } else {
                // Navigate to practice test activity
                val practiceTestIntent = Intent(this, AIchatActivity::class.java)
                startActivity(practiceTestIntent)
                finish()
            }
        }

        practiceTestContainer.setOnClickListener {
            // Navigate to practice test activity
            val practiceTestIntent = Intent(this, DrivingTestActivity::class.java)
            startActivity(practiceTestIntent)
            finish()
        }

        testLocation.setOnClickListener {
            // Navigate to question review activity
            val testLocation = Intent(this, MapActivity::class.java)
            startActivity(testLocation)
            finish()
        }
    }

    // Will be completed once question part is done
    private fun progress() {
        progressBar = findViewById(R.id.user_progress_bar)

        var currentPosition = 0

        // logic to set progress bar based on question count
        progressBar.progress = questionCount

        // logic to set progress text based on question count
        database = Firebase.database.reference
        database.child("users").child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue<User>()
                // Do something with the user data
                if (user != null) {
                    currentPosition = user.currentQuestionPosition
                }

                progressText.text = "$currentPosition / ${progressBar.max}"
                progressBar.progress = currentPosition
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(ContentValues.TAG, "Failed to read user data.", error.toException())
            }
        })

    }

    /**
     * Initialize the toolbar
     */
    private fun initialToolBar()
    {
        navToolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(navToolbar)
        supportActionBar?.title = ""
    }

    /**
     * Set the language to the opposite of the current language
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.action_register -> {
                // Handle login
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_select_language -> {
                showLanguageSelectionDialog()
            }
            R.id.action_log_out -> {

                val user = Firebase.auth.currentUser
                if (user != null && user.isAnonymous) {
                    showAnonymousLogoutAlert()
                } else {
                    // Regular logout for non-anonymous users
                    performLogout()
                }
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }


    /**
     * Creates the menu for the toolbar
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * Creates a notification channel for the app
     */
    private fun createNotificationChannel() {
        val channelId = "default_channel"
        val channelName = "Default Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = "Channel description" // Set your channel description here
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    /**
     * Log out the user
     */
    private fun performLogout() {
        Firebase.auth.signOut()
        // Redirect to login or other appropriate activity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Show alert dialog
     */
    private fun showAlert(messageResId: Int) {
        val message = getString(messageResId)
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
                val register = Intent(this, RegisterActivity::class.java)
                startActivity(register)
                finish()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .create().show()
    }

    /**
     * Show language selection dialog
     */
    private fun showLanguageSelectionDialog() {
        val languages = resources.getStringArray(R.array.language_options)
        val languageCodes = arrayOf("en", "zh")

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.select_language))
            .setItems(languages) { _, which ->
                setLanguage(languageCodes[which])
                recreate() // Recreate the activity to apply the new language
            }
            .create()
            .show()
    }

    /**
     * Show alert dialog for anonymous user logout
     */
    private fun showAnonymousLogoutAlert() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.anonymous_logout_title))
            .setMessage(getString(R.string.anonymous_logout_message))
            .setPositiveButton(getString(R.string.log_out)) { dialog, _ ->
                performLogout()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.register_a_new_account)) { dialog, _ ->
                // Redirect to a registration or account linking activity
                val registerIntent = Intent(this, RegisterActivity::class.java)
                startActivity(registerIntent)
                dialog.dismiss()
            }
            .setNeutralButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .show()
    }

}
