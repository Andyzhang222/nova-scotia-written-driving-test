package com.example.novascotiawrittendrivingtest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    private  lateinit var practiceTestContainer : CardView
    private lateinit var questionReviewContainer: CardView
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private var questionCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        practiceTestContainer = findViewById(R.id.practiceTestContainer)
        questionReviewContainer = findViewById(R.id.questionReviewContainer)

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
}
