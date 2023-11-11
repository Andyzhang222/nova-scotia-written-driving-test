package com.example.novascotiawrittendrivingtest

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class DrivingTestActivity : AppCompatActivity() {

    private lateinit var tvQuestion: TextView
    private lateinit var imageView: ImageView
    private lateinit var tvOptionOne: TextView
    private lateinit var tvOptionTwo: TextView
    private lateinit var tvOptionThree: TextView
    private lateinit var tvOptionFour: TextView
    private lateinit var btnSubmit: Button
    private lateinit var pbProgress: ProgressBar
    private lateinit var tvProgress: TextView

    private lateinit var questionsList: ArrayList<Question>

    private var selectedPosition: Int = 0
    private var correctAnswer: Int = 0
    private var currentPosition: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.driving_test_layout)

        initializeViews()

        questionsList = Constants.getQuestion()
        initializeQuestion()

        tvOptionOne.setOnClickListener { setOptionClick(it) }
        tvOptionTwo.setOnClickListener { setOptionClick(it) }
        tvOptionThree.setOnClickListener { setOptionClick(it) }
        tvOptionFour.setOnClickListener { setOptionClick(it) }

        btnSubmit.setOnClickListener { setOptionClick(it) }
    }

    private fun initializeViews() {
        tvQuestion = findViewById(R.id.tv_question)
        imageView = findViewById(R.id.imageView)
        tvOptionOne = findViewById(R.id.tv_optionOne)
        tvOptionTwo = findViewById(R.id.tv_optionTwo)
        tvOptionThree = findViewById(R.id.tv_optionThree)
        tvOptionFour = findViewById(R.id.tv_optionFour)
        btnSubmit = findViewById(R.id.btnSubmit)
        pbProgress = findViewById(R.id.pb)
        tvProgress = findViewById(R.id.tv_progress)
    }

    private fun initializeQuestion() {
        btnSubmit.isEnabled = false

        val question: Question = questionsList[currentPosition - 1]

        tvQuestion.text = question.question
        imageView.setImageResource(question.image)
        tvOptionOne.text = question.optionOne
        tvOptionTwo.text = question.optionTwo
        tvOptionThree.text = question.optionThree
        tvOptionFour.text = question.optionFour

        pbProgress.progress = currentPosition
        tvProgress.text = "$currentPosition / ${pbProgress.max}"

        setDefault(tvOptionOne)
        setDefault(tvOptionTwo)
        setDefault(tvOptionThree)
        setDefault(tvOptionFour)

        btnSubmit.text = if (currentPosition == questionsList.size) "Finish Quiz" else "Answer"
    }

    private fun setDefault(v: TextView) {
        v.setTextColor(Color.parseColor("#000000"))
        v.typeface = Typeface.DEFAULT
        v.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
    }

    private fun setOptionClick(v: View?) {
        btnSubmit.isEnabled = true
        when (v?.id) {
            R.id.tv_optionOne -> selectedOptionView(tvOptionOne, 1)
            R.id.tv_optionTwo -> selectedOptionView(tvOptionTwo, 2)
            R.id.tv_optionThree -> selectedOptionView(tvOptionThree, 3)
            R.id.tv_optionFour -> selectedOptionView(tvOptionFour, 4)
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
        if (currentPosition <= questionsList.size) {
            initializeQuestion()
        } else {
            //TODO: navigateToScore() // Uncomment or implement this when needed
        }
    }

    private fun evaluateSelectedOption() {
        val question = questionsList[currentPosition - 1]
        if (question.correctAnswer != selectedPosition) {
            answerUpdate(selectedPosition, R.drawable.wrong_option_border_bg)
        } else {
            correctAnswer++
        }

        answerUpdate(question.correctAnswer, R.drawable.correct_option_border_bg)

        btnSubmit.text = if (currentPosition == questionsList.size) "Finish Quiz" else "Next Question"

        selectedPosition = 0
    }


    private fun selectedOptionView(tv: TextView, selectedPosition: Int) {
        setDefault(tvOptionOne)
        setDefault(tvOptionTwo)
        setDefault(tvOptionThree)
        setDefault(tvOptionFour)

        this.selectedPosition = selectedPosition

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    private fun answerUpdate(selectedOption: Int, drawableView: Int) {
        when (selectedOption) {
            1 -> tvOptionOne.background = ContextCompat.getDrawable(this, drawableView)
            2 -> tvOptionTwo.background = ContextCompat.getDrawable(this, drawableView)
            3 -> tvOptionThree.background = ContextCompat.getDrawable(this, drawableView)
            4 -> tvOptionFour.background = ContextCompat.getDrawable(this, drawableView)
        }
    }

//To do: Uncomment or implement this when needed
//    private fun navigateToScore() {
//        val intent = Intent(this, ScoreActivity::class.java)
//        intent.putExtra("score", mCorrectAnswer)
//        // Add other extras as needed
//        startActivity(intent)
//    }
}



