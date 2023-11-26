package com.example.novascotiawrittendrivingtest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import java.util.Locale

class EmptyWrongActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val language = getUserSelectedLanguage()
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        setContentView(R.layout.activity_empty_wrong)

        // set click listener for back button
        backButton = findViewById(R.id.backButton_empty)
        backButton.setOnClickListener { navigateToMain() }
    }

    private fun getUserSelectedLanguage(): String {
        val sharedPref = this.getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("SelectedLanguage", "en") ?: "en" // Default to English
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}