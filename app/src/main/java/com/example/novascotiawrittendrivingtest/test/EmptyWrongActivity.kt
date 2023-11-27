package com.example.novascotiawrittendrivingtest.test

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.novascotiawrittendrivingtest.MainActivity
import com.example.novascotiawrittendrivingtest.R
import java.util.Locale

class EmptyWrongActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set language
        val language = getUserSelectedLanguage()
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Set content view
        setContentView(R.layout.activity_empty_wrong)

        // set click listener for back button
        backButton = findViewById(R.id.backButton_empty)
        backButton.setOnClickListener { navigateToMain() }
    }

    /**
     * Navigate to main activity
     */
    private fun getUserSelectedLanguage(): String {
        val sharedPref = this.getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("SelectedLanguage", "en") ?: "en" // Default to English
    }

    /**
     * Navigate to main activity
     */
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}