package com.example.novascotiawrittendrivingtest

object QuestionBank {

    fun getQuestion() : ArrayList<Question>{

        val questionsList = ArrayList<Question>()

        questionsList.add(Question(
            1,
            "What does this sign mean?",
            R.drawable.question1,
            "There is a sharp right turn ahead",
            "There is a sharp left turn ahead",
            "There is a slight right curve ahead",
            "There is a slight left curve ahead",
            3
        ))

        questionsList.add(Question(
            2,
            "What does this sign mean?",
            R.drawable.question2,
            "Drivers must keep left of the traffic island",
            "Drivers must take the left turn",
            "Drivers must take the right turn",
            "Drivers must keep right of the traffic island",
            4
        ))

        questionsList.add(Question(
            3,
            "What does this sign mean?",
            R.drawable.question3,
            "Drivers can only park their vehicle during the mentioned time",
            "Drivers can only park their vehicles during other than the time mentioned",
            "Drivers cannot park their vehicles in this area",
            "This is a private parking area",
            1
        ))

        questionsList.add(Question(
            4,
            "What does this sign mean?",
            R.drawable.question4,
            "Drivers must come to a complete stop",
            "There is a stop sign ahead",
            "There is a dead-end ahead",
            "There is a construction zone ahead",
            2
        ))

        questionsList.add(Question(
            5,
            "What does this sign mean?",
            R.drawable.question5,
            "The 40km/hr is the lowest maximum speed limit in the area ahead when flashing",
            "The flashing indicates the oncoming of train in the railway crossing the road ahead",
            "There is a speed limit in the area ahead when lights are not flashing",
            "There is a speed limit in the area ahead when lights are flashing",
            4
        ))

        // Question 6
        questionsList.add(Question(
            6,
            "What does this sign mean?",
            R.drawable.question6,
            "Drivers from the side-road don’t have a clear view ahead at intersection",
            "Drivers must go straight through the intersection ahead",
            "Drivers must turn to the right at intersection",
            "Drivers must come to a complete stop",
            1
        ))

        // Question 7
        questionsList.add(Question(
            7,
            "What does this sign mean?",
            R.drawable.question7,
            "The left lane ends ahead",
            "The rightmost lane ends ahead",
            "There is a sharp left turn ahead",
            "There is a slight right turn ahead",
            2
        ))

        // Question 8
        questionsList.add(Question(
            8,
            "What does this sign mean?",
            R.drawable.question8,
            "Drivers must go straight at the intersection",
            "There is a dead-end ahead",
            "Drivers must not drive through the intersection",
            "The road is turning ahead",
            3
        ))

        // Question 9
        questionsList.add(Question(
            9,
            "What does this sign mean?",
            R.drawable.question9,
            "Drivers must turn left at the intersection",
            "Drivers must not turn left at the intersection",
            "Drivers must not enter in the intersection",
            "Drivers must not drive their vehicle on the left lane",
            2
        ))

        questionsList.add(Question(
            10,
            "What does this sign mean?",
            R.drawable.question10,
            "Drivers must keep their vehicles speed above 50 km/hr in the area ahead",
            "There is a speed limit of 50 km/hr in the area ahead",
            "Ending of a Speed limit zone",
            "There is a speed limit for longer vehicles usually 40m in length",
            2
        ))

        return questionsList
    }

}