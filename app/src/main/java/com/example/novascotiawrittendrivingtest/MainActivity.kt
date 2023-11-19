package com.example.novascotiawrittendrivingtest

import android.os.Bundle
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private var questionCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}
