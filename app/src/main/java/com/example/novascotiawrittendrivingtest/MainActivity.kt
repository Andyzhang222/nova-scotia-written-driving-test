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

class MainActivity : AppCompatActivity() {

    private  lateinit var practiceTestContainer : CardView
    private lateinit var questionReviewContainer: CardView
    private lateinit var testLocation: CardView
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private var questionCount = 0
    private lateinit var navToolbar: Toolbar
    private lateinit var database: DatabaseReference
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE)
        val language = sharedPref.getString("SelectedLanguage", Locale.getDefault().language)
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        setContentView(R.layout.activity_main)

        val user = Firebase.auth.currentUser
        user?.let {
            userId = it.uid
        }

        practiceTestContainer = findViewById(R.id.practiceTestContainer)
        questionReviewContainer = findViewById(R.id.questionReviewContainer)
        testLocation=findViewById(R.id.testLocationContainer)

        progressText = findViewById(R.id.progressText)

        initialToolBar()

        practiceTestContainer.setOnClickListener(){
            // Navigate to practice test activity
            val practiceTestIntent = Intent(this, DrivingTestActivity::class.java)
            startActivity(practiceTestIntent)
            finish()
        }

        questionReviewContainer.setOnClickListener(){
            // Navigate to question review activity
            val questionReviewIntent = Intent(this, WrongQuestionReviewActivity::class.java)
            startActivity(questionReviewIntent)
            finish()
        }

       testLocation.setOnClickListener(){
            // Navigate to question review activity
            val testLocation= Intent(this, MapsActivity::class.java)
            startActivity(testLocation)
            finish()
        }

        progress()
    }

    // Will be completed once question part is done
    private fun progress() {
        progressBar = findViewById<ProgressBar>(R.id.user_progress_bar)

        var currentPosition = 0

        // logic to set progress bar based on question count
        progressBar.progress = questionCount

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

    private fun initialToolBar()
    {
        navToolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(navToolbar)
        supportActionBar?.setTitle("")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.action_log_out -> {

                Firebase.auth.signOut()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}
