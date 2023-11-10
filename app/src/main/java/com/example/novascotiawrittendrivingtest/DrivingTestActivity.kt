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

    private lateinit var mQuestionsList: ArrayList<Question>

    private var mSelectedPosition: Int = 0
    private var mCorrectAnswer: Int = 0
    private var mCurrentPosition: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.driving_test_layout)

        tvQuestion = findViewById(R.id.tv_question)
        imageView = findViewById(R.id.imageView)
        tvOptionOne = findViewById(R.id.tv_optionOne)
        tvOptionTwo = findViewById(R.id.tv_optionTwo)
        tvOptionThree = findViewById(R.id.tv_optionThree)
        tvOptionFour = findViewById(R.id.tv_optionFour)
        btnSubmit = findViewById(R.id.btnSubmit)
        pbProgress = findViewById(R.id.pb)
        tvProgress = findViewById(R.id.tv_progress)

        mQuestionsList = Constants.getQuestion()
        setQuestion()

        tvOptionOne.setOnClickListener {
            setOptionClick(it)
        }
        tvOptionTwo.setOnClickListener {
            setOptionClick(it)
        }
        tvOptionThree.setOnClickListener {
            setOptionClick(it)
        }
        tvOptionFour.setOnClickListener {
            setOptionClick(it)
        }
        btnSubmit.setOnClickListener {
            setOptionClick(it)
        }
    }

    private fun setQuestion() {
        val question: Question = mQuestionsList[mCurrentPosition - 1]

        tvQuestion.text = question.question
        imageView.setImageResource(question.image)
        tvOptionOne.text = question.optionOne
        tvOptionTwo.text = question.optionTwo
        tvOptionThree.text = question.optionThree
        tvOptionFour.text = question.optionFour

        pbProgress.progress = mCurrentPosition
        tvProgress.text = "$mCurrentPosition / ${pbProgress.max}"

        defaultAppearance()

        if (mCurrentPosition == mQuestionsList.size) {
            btnSubmit.text = "Finish Quiz"
        } else {
            btnSubmit.text = "Submit"
        }
    }

    private fun defaultAppearance() {
        val options = ArrayList<TextView>()
        options.add(0, tvOptionOne)
        options.add(1, tvOptionTwo)
        options.add(2, tvOptionThree)
        options.add(3, tvOptionFour)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    fun setOptionClick(v: View?) {
        when (v?.id) {
            R.id.tv_optionOne -> selectedOptionView(tvOptionOne, 1)
            R.id.tv_optionTwo -> selectedOptionView(tvOptionTwo, 2)
            R.id.tv_optionThree -> selectedOptionView(tvOptionThree, 3)
            R.id.tv_optionFour -> selectedOptionView(tvOptionFour, 4)
            R.id.btnSubmit -> {
                if (mSelectedPosition == 0) {
                    mCurrentPosition++
                    when {
                        mCurrentPosition <= mQuestionsList.size -> setQuestion()
//                        else -> navigateToScore()
                    }
                } else {
                    val question = mQuestionsList[mCurrentPosition - 1]
                    if (question.correctAnswer != mSelectedPosition) {
                        answerView(mSelectedPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswer++
                    }

                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList.size) {
                        btnSubmit.text = "Finish Quiz"
                    } else {
                        btnSubmit.text = "Next Question"
                    }
                    mSelectedPosition = 0
                }
            }
        }
    }


    private fun selectedOptionView(tv: TextView, selectedPosition: Int) {
        defaultAppearance()
        mSelectedPosition = selectedPosition

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    private fun answerView(selectedOption: Int, drawableView: Int) {
        when (selectedOption) {
            1 -> tvOptionOne.background = ContextCompat.getDrawable(this, drawableView)
            2 -> tvOptionTwo.background = ContextCompat.getDrawable(this, drawableView)
            3 -> tvOptionThree.background = ContextCompat.getDrawable(this, drawableView)
            4 -> tvOptionFour.background = ContextCompat.getDrawable(this, drawableView)
        }
    }

//    private fun navigateToScore() {
//        val intent = Intent(this, ScoreActivity::class.java)
//        intent.putExtra("score", mCorrectAnswer)
//        // Add other extras as needed
//        startActivity(intent)
//    }
}



