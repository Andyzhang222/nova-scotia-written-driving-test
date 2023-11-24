package com.example.novascotiawrittendrivingtest

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import android.widget.TextView
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    private  lateinit var practiceTestContainer : CardView
    private lateinit var questionReviewContainer: CardView
    private lateinit var testLocation: CardView
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private var questionCount = 0
    private lateinit var navToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialToolBar()

        practiceTestContainer = findViewById(R.id.practiceTestContainer)
        questionReviewContainer = findViewById(R.id.questionReviewContainer)
        testLocation=findViewById(R.id.testLocationContainer)

        progressText = findViewById(R.id.progressText)

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

        // logic to set progress bar based on question count
        fun run() {
            progressBar.progress = questionCount
            progressText.text = "$questionCount / ${progressBar.max}"
        }
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
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
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
