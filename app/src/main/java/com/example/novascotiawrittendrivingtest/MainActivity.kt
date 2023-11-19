package com.example.novascotiawrittendrivingtest

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private var questionCount = 0
    private lateinit var navToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialToolBar()
        progress()
    }

    // Will be completed once question part is done
    private fun progress() {
        progressBar = findViewById<ProgressBar>(R.id.user_progress_bar)

        // logic to set progress bar based on question count
        fun run() {
            progressBar.progress = questionCount
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
