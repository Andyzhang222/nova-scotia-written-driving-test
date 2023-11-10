package com.example.novascotiawrittendrivingtest

object Constants {

    fun getQuestion() : ArrayList<Question>{

        val questionsList = ArrayList<Question>()

        val que1 = Question(1,
            "chose the correct answer?",
            R.drawable.question1,
            "a.\tThere is a sharp right turn ahead",
            "b.\tThere is a sharp left turn ahead",
            "c.\tThere is a slight right curve ahead",
            "d.\tThere is a slight left curve ahead",
            3)

        questionsList.add(que1)

        return questionsList
    }

}