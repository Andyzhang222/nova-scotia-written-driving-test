package com.example.novascotiawrittendrivingtest.test

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.novascotiawrittendrivingtest.MainActivity
import com.example.novascotiawrittendrivingtest.R
import com.example.novascotiawrittendrivingtest.dataClass.User
import com.example.novascotiawrittendrivingtest.dataClass.Question
import com.example.novascotiawrittendrivingtest.question.QuestionBank
import com.example.novascotiawrittendrivingtest.question.QuestionBank_CN
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import java.util.Locale

class WrongQuestionReviewActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var questionImage: ImageView
    private lateinit var optionOne: TextView
    private lateinit var optionTwo: TextView
    private lateinit var optionThree: TextView
    private lateinit var optionFour: TextView
    private lateinit var btnSubmit: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private lateinit var backButton: ImageView
    private lateinit var restartButton: ImageView

    private lateinit var questionsList: ArrayList<Question>
    private var incorrectQuestionsList: List<String> = listOf()

    private var selectedPosition: Int = 0
    private var correctAnswer: Int = 0
    private var currentPosition: Int = 0
    private var correctness: Boolean = false

    private lateinit var database: DatabaseReference
    private lateinit var userId: String

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
        setContentView(R.layout.driving_test_layout)

        // Initialize firebase database
        database = Firebase.database.reference
        val user = Firebase.auth.currentUser
        user?.let {
            userId = it.uid
            fetchIncorrectQuestionIds(userId)
        }

        // initialize views
        initializeViews()

        // initialize question based on last time question position
        database.child("users").child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue<User>()
                // Do something with the user data
                if (user != null) {
                    currentPosition = user.currentPositionInWrongQuestion
                }
                    initializeQuestion()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read user data.", error.toException())
            }
        })

        // set click listeners for question options
        optionOne.setOnClickListener { setClick(it) }
        optionTwo.setOnClickListener { setClick(it) }
        optionThree.setOnClickListener { setClick(it) }
        optionFour.setOnClickListener { setClick(it) }

        // set click listener for submit button
        btnSubmit.setOnClickListener { setClick(it) }

        // set click listener for back button
        backButton.setOnClickListener { navigateToMain() }

        // set click listener for restart button
        restartButton.setOnClickListener {
            currentPosition = 0
            correctAnswer = 0
            initializeQuestion()
        }
    }

    /**
     * Initialize views
     */
    private fun initializeViews() {
        questionTextView = findViewById(R.id.questionText)
        questionImage = findViewById(R.id.imageView)
        optionOne = findViewById(R.id.optionOne)
        optionTwo = findViewById(R.id.optionTwo)
        optionThree = findViewById(R.id.optionThree)
        optionFour = findViewById(R.id.optionFour)
        btnSubmit = findViewById(R.id.btnSubmit)
        progressBar = findViewById(R.id.progressBar)
        progressText = findViewById(R.id.progressText)
        restartButton = findViewById(R.id.restartButton)
        backButton = findViewById(R.id.backButton)
    }

    /**
     * Initialize question
     */
    private fun initializeQuestion() {
        // Submit button is disabled when no answer is selected
        btnSubmit.isEnabled = false
        btnSubmit.setBackgroundColor(Color.rgb(222, 219, 213))

        // Get current question
        val question: Question = questionsList[currentPosition]

        // Set question and options
        questionTextView.text = question.question
        questionImage.setImageResource(question.image)
        optionOne.text = question.optionOne
        optionTwo.text = question.optionTwo
        optionThree.text = question.optionThree
        optionFour.text = question.optionFour

        // Update progress bar and text
        progressBar.max = questionsList.size
        progressBar.progress = currentPosition
        progressText.text = "${currentPosition}/ ${incorrectQuestionsList.size}"

        // Set default option view layout
        setDefault(optionOne)
        setDefault(optionTwo)
        setDefault(optionThree)
        setDefault(optionFour)

        // Set submit button text
        btnSubmit.text = if (currentPosition == questionsList.size) getString(R.string.finish_quiz) else getString(
            R.string.answer
        )

    }

    /**
     * Set default option view layout
     */
    private fun setDefault(v: TextView) {
        v.setTextColor(Color.parseColor("#000000"))
        v.typeface = Typeface.DEFAULT
        v.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
    }

    /**
     * Set option click handler
     */
    private fun setClick(v: View?) {
        // Enable submit button
        btnSubmit.isEnabled = true
        // Set selected option view
        when (v?.id) {
            R.id.optionOne -> selectedOptionView(optionOne, 1)
            R.id.optionTwo -> selectedOptionView(optionTwo, 2)
            R.id.optionThree -> selectedOptionView(optionThree, 3)
            R.id.optionFour -> selectedOptionView(optionFour, 4)
            R.id.btnSubmit -> handleSubmitButtonClicked()
        }
        btnSubmit.setBackgroundColor(Color.parseColor("#4D6BD9"))
    }

    /**
     * Handle submit button click
     */
    private fun handleSubmitButtonClicked() {
        // If there is no selected option, question is being answered, go next question, else evaluate selected option
        if (selectedPosition == 0) {
            handleNextQuestion()
        } else {
            evaluateSelectedOption()

            if (currentPosition == questionsList.size - 1) {
                showResetAlert()
            }
        }
    }

    /**
     * Show reset alert
     */
    private fun showResetAlert() {
        // Create an AlertDialog builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.quiz_complete))
        builder.setMessage(getString(R.string.quiz_complete_message))

        // Add the buttons
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            // User clicked 'Yes' button. Reset the questions and restart the quiz.
            currentPosition = 0
            correctAnswer = 0
            initializeQuestion()
            updateUserInFirebase(userId, currentPosition)
        }.
        setNeutralButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }

        // Create and show the AlertDialog
        val dialog = builder.create()
        dialog.show()
    }

    /**
     * Handle go to next question
     */
    private fun handleNextQuestion() {
        if (currentPosition + 1 < questionsList.size) {
            currentPosition++
            updateUserInFirebase(userId, currentPosition)
            initializeQuestion()
        } else {
            navigateToMain()
        }
    }

    /**
     * Evaluate selected option
     */
    private fun evaluateSelectedOption() {
        // Get current question
        val question = questionsList[currentPosition]
        // If selected option is correct, increment correct answer count, else update the layout of wrong answer
        correctness = if (question.correctAnswer != selectedPosition) {
            answerUpdate(selectedPosition, R.drawable.wrong_option_border_bg)
            false
        } else {
            correctAnswer++
            true
        }

        // Show correct answer
        answerUpdate(question.correctAnswer, R.drawable.correct_option_border_bg)

        // Set submit button text
        btnSubmit.text = if (currentPosition == questionsList.size - 1) getString(R.string.finish_quiz) else getString(
            R.string.next_question
        )

        // Reset selected position
        selectedPosition = 0

        // Update progress bar and text
        progressBar.progress = currentPosition + 1
        progressText.text = "${currentPosition + 1}/ ${incorrectQuestionsList.size}"
    }

    /**
     * Set selected option view
     */
    private fun selectedOptionView(tv: TextView, selectedPosition: Int) {
        setDefault(optionOne)
        setDefault(optionTwo)
        setDefault(optionThree)
        setDefault(optionFour)

        // Set selected position
        this.selectedPosition = selectedPosition

        // Set selected option view layout
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    /**
     * Used for updating correct answer and wrong answer drawables
     */
    private fun answerUpdate(selectedOption: Int, drawableView: Int) {
        when (selectedOption) {
            1 -> optionOne.background = ContextCompat.getDrawable(this, drawableView)
            2 -> optionTwo.background = ContextCompat.getDrawable(this, drawableView)
            3 -> optionThree.background = ContextCompat.getDrawable(this, drawableView)
            4 -> optionFour.background = ContextCompat.getDrawable(this, drawableView)
        }
    }

    /**
     * Update user test state in firebase
     */
    private fun updateUserInFirebase(userId: String, currentPosition: Int) {
        val userUpdate = mapOf("currentPositionInWrongQuestion" to currentPosition)
        database.child("users").child(userId).updateChildren(userUpdate)
            .addOnSuccessListener {
                Log.d(TAG, "User data updated successfully.")
            }
            .addOnFailureListener {
                Log.e(TAG, "Failed to update user data.", it)
            }
    }

    /**
     * Fetch incorrect question ids from firebase
     */
    private fun fetchIncorrectQuestionIds(userId: String) {
        val incorrectQuestionsRef = database.child("users").child(userId).child("incorrectQuestions")
        incorrectQuestionsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Get incorrect question ids
                    incorrectQuestionsList = snapshot.children.mapNotNull { it.key }
                    questionsList = if (getUserSelectedLanguage() == "zh") {
                        QuestionBank_CN.getQuestionsByIds(incorrectQuestionsList)
                    } else {
                        QuestionBank.getQuestionsByIds(incorrectQuestionsList)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "Database error: $databaseError")
            }
        })
    }

    /**
     * Retrieves the user's saved language preference
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
        // Add other extras as needed
        startActivity(intent)
        finish()
    }
}
