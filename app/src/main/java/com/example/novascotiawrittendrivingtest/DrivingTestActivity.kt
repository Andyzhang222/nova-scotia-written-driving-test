package com.example.novascotiawrittendrivingtest

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class DrivingTestActivity : AppCompatActivity() {

    private lateinit var imageViewQuestion: ImageView
    private lateinit var radioGroupOptions: RadioGroup
    private lateinit var radioButtonOption1: RadioButton
    private lateinit var radioButtonOption2: RadioButton
    private lateinit var radioButtonOption3: RadioButton
    private lateinit var radioButtonOption4: RadioButton
    private lateinit var buttonNext: Button


    private val questions: MutableList<Question> = mutableListOf()
    private var currentQuestionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.driving_test_layout)


//        imageViewQuestion = findViewById(R.id.image)
//        radioGroupOptions = findViewById(R.id.radioGroupOptions)
//        radioButtonOption1 = findViewById(R.id.radioButtonOption1)
//        radioButtonOption2 = findViewById(R.id.radioButtonOption2)
//        radioButtonOption3 = findViewById(R.id.radioButtonOption3)
//        radioButtonOption4 = findViewById(R.id.radioButtonOption4)
//        buttonNext = findViewById(R.id.buttonSubmit)


        loadQuestions()


        buttonNext.setOnClickListener {
            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                showQuestion(currentQuestionIndex)
            } else {

                currentQuestionIndex = 0
                showQuestion(currentQuestionIndex)
            }
        }


        showQuestion(currentQuestionIndex)
    }


    private fun loadQuestions() {

        questions.add(Question(
            questionText = getString(R.string.question_1_text),
            options = listOf(
                getString(R.string.question_1_option_a),
                getString(R.string.question_1_option_b),
                getString(R.string.question_1_option_c),
                getString(R.string.question_1_option_d)
            ),
            imageResId = R.drawable.q1,
            correctAnswer = getString(R.string.question_1_correct)
        ))

    }


    private fun showQuestion(index: Int) {
        val question = questions[index]
        imageViewQuestion.setImageResource(question.imageResId)
        radioButtonOption1.text = question.options[0]
        radioButtonOption2.text = question.options[1]
    }
}


