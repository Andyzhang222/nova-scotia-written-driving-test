package com.example.novascotiawrittendrivingtest

import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class DrivingTestActivity : AppCompatActivity() {

    // create two users used for testing
//    val user1 = User(userId = "1", currentQuestionPosition = 0)
//    val user2 = User(userId = "2", currentQuestionPosition = 0)

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

    private var selectedPosition: Int = 0
    private var correctAnswer: Int = 0
    private var currentPosition: Int = 0

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.driving_test_layout)

        initializeViews()

        questionsList = QuestionBank.getQuestion()

        // initialize question based on last time question position
        database = Firebase.database.reference
        database.child("users").child("1").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue<User>()
                // Do something with the user data
                if (user != null) {
                    currentPosition = user.currentQuestionPosition
                    initializeQuestion()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
            }
        })

        optionOne.setOnClickListener { setOptionClick(it) }
        optionTwo.setOnClickListener { setOptionClick(it) }
        optionThree.setOnClickListener { setOptionClick(it) }
        optionFour.setOnClickListener { setOptionClick(it) }

        btnSubmit.setOnClickListener { setOptionClick(it) }
        backButton.setOnClickListener { navigateToMain() }
        restartButton.setOnClickListener {
            currentPosition = 0
            correctAnswer = 0
            initializeQuestion()
        }
    }

    private fun initializeViews() {
        questionTextView = findViewById(R.id.questionText)
        questionImage = findViewById(R.id.imageView)
        optionOne = findViewById(R.id.optionOne)
        optionTwo = findViewById(R.id.optionTwo)
        optionThree = findViewById(R.id.optionThree)
        optionFour = findViewById(R.id.optionFour)
        btnSubmit = findViewById(R.id.btnSubmit)
        progressBar = findViewById(R.id.progressText)
        progressText = findViewById(R.id.tv_progress)
        restartButton = findViewById(R.id.restartButton)
        backButton = findViewById(R.id.backButton)
    }

    private fun initializeQuestion() {
        btnSubmit.isEnabled = false

        val question: Question = questionsList[currentPosition]

        questionTextView.text = question.question
        questionImage.setImageResource(question.image)
        optionOne.text = question.optionOne
        optionTwo.text = question.optionTwo
        optionThree.text = question.optionThree
        optionFour.text = question.optionFour

        progressBar.progress = currentPosition
        progressText.text = "$currentPosition / ${progressBar.max}"

        setDefault(optionOne)
        setDefault(optionTwo)
        setDefault(optionThree)
        setDefault(optionFour)

        btnSubmit.text = if (currentPosition == questionsList.size - 1) "Finish Quiz" else "Answer"
    }

    private fun setDefault(v: TextView) {
        v.setTextColor(Color.parseColor("#000000"))
        v.typeface = Typeface.DEFAULT
        v.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
    }

    private fun setOptionClick(v: View?) {
        btnSubmit.isEnabled = true
        when (v?.id) {
            R.id.optionOne -> selectedOptionView(optionOne, 1)
            R.id.optionTwo -> selectedOptionView(optionTwo, 2)
            R.id.optionThree -> selectedOptionView(optionThree, 3)
            R.id.optionFour -> selectedOptionView(optionFour, 4)
            R.id.btnSubmit -> handleSubmitButtonClicked()
        }
    }

    private fun handleSubmitButtonClicked() {
        if (selectedPosition == 0) {
            handleNoOptionSelected()
        } else {
            evaluateSelectedOption()
        }
    }

    private fun handleNoOptionSelected() {
        currentPosition++
        updateUserInFirebase("1", currentPosition)
        if (currentPosition < questionsList.size) {
            initializeQuestion()
        } else {
            navigateToMain()
        }
    }

    private fun evaluateSelectedOption() {
        val question = questionsList[currentPosition]
        if (question.correctAnswer != selectedPosition) {
            answerUpdate(selectedPosition, R.drawable.wrong_option_border_bg)
        } else {
            correctAnswer++
        }

        answerUpdate(question.correctAnswer, R.drawable.correct_option_border_bg)

        btnSubmit.text = if (currentPosition == questionsList.size - 1) "Finish Quiz" else "Next Question"

        selectedPosition = 0
    }


    private fun selectedOptionView(tv: TextView, selectedPosition: Int) {
        setDefault(optionOne)
        setDefault(optionTwo)
        setDefault(optionThree)
        setDefault(optionFour)

        this.selectedPosition = selectedPosition

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    private fun answerUpdate(selectedOption: Int, drawableView: Int) {
        when (selectedOption) {
            1 -> optionOne.background = ContextCompat.getDrawable(this, drawableView)
            2 -> optionTwo.background = ContextCompat.getDrawable(this, drawableView)
            3 -> optionThree.background = ContextCompat.getDrawable(this, drawableView)
            4 -> optionFour.background = ContextCompat.getDrawable(this, drawableView)
        }
    }

    private fun updateUserInFirebase(userId: String, currentPosition: Int) {
        val userUpdate = mapOf("currentQuestionPosition" to currentPosition)
        database.child("users").child(userId).updateChildren(userUpdate)
            .addOnSuccessListener {
                Log.d(TAG, "User data updated successfully.")
            }
            .addOnFailureListener {
                Log.e(TAG, "Failed to update user data.", it)
            }
    }

    //To do: Uncomment or implement this when needed
    private fun navigateToMain() {
//        val intent = Intent(this, ScoreActivity::class.java)
//        intent.putExtra("score", mCorrectAnswer)
//        // Add other extras as needed
//        startActivity(intent)
    }
}



