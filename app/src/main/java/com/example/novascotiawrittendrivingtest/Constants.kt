package com.example.novascotiawrittendrivingtest

object Constants {

    fun getQuestion() : ArrayList<Question>{

        val questionsList = ArrayList<Question>()

        questionsList.add(Question(
            1,
            "What does this sign mean?",
            R.drawable.question1,
            "a. There is a sharp right turn ahead",
            "b. There is a sharp left turn ahead",
            "c. There is a slight right curve ahead",
            "d. There is a slight left curve ahead",
            3
        ))

        questionsList.add(Question(
            2,
            "What does this sign mean?",
            R.drawable.question2,
            "a. Drivers must keep left of the traffic island",
            "b. Drivers must take the left turn",
            "c. Drivers must take the right turn",
            "d. Drivers must keep right of the traffic island",
            4
        ))

        questionsList.add(Question(
            3,
            "What does this sign mean?",
            R.drawable.question3,
            "a. Drivers can only park their vehicle during the mentioned time",
            "b. Drivers can only park their vehicles during other than the time mentioned",
            "c. Drivers cannot park their vehicles in this area",
            "d. This is a private parking area",
            1
        ))

        questionsList.add(Question(
            4,
            "What does this sign mean?",
            R.drawable.question4,
            "a. Drivers must come to a complete stop",
            "b. There is a stop sign ahead",
            "c. There is a dead-end ahead",
            "d. There is a construction zone ahead",
            2
        ))

        questionsList.add(Question(
            5,
            "What does this sign mean?",
            R.drawable.question5,
            "a. The 40km/hr is the lowest maximum speed limit in the area ahead when flashing",
            "b. The flashing indicates the oncoming of train in the railway crossing the road ahead",
            "c. There is a speed limit in the area ahead when lights are not flashing",
            "d. There is a speed limit in the area ahead when lights are flashing",
            4
        ))

        // Question 6
        questionsList.add(Question(
            6,
            "What does this sign mean?",
            R.drawable.question6,
            "a. Drivers from the side-road don’t have a clear view ahead at intersection",
            "b. Drivers must go straight through the intersection ahead",
            "c. Drivers must turn to the right at intersection",
            "d. Drivers must come to a complete stop",
            1
        ))

        // Question 7
        questionsList.add(Question(
            7,
            "What does this sign mean?",
            R.drawable.question7,
            "a. The left lane ends ahead",
            "b. The rightmost lane ends ahead",
            "c. There is a sharp left turn ahead",
            "d. There is a slight right turn ahead",
            2
        ))

        // Question 8
        questionsList.add(Question(
            8,
            "What does this sign mean?",
            R.drawable.question8,
            "a. Drivers must go straight at the intersection",
            "b. There is a dead-end ahead",
            "c. Drivers must not drive through the intersection",
            "d. The road is turning ahead",
            3
        ))

        // Question 9
        questionsList.add(Question(
            9,
            "What does this sign mean?",
            R.drawable.question9,
            "a. Drivers must turn left at the intersection",
            "b. Drivers must not turn left at the intersection",
            "c. Drivers must not enter in the intersection",
            "d. Drivers must not drive their vehicle on the left lane",
            2
        ))

        questionsList.add(Question(
            10,
            "What does this sign mean?",
            R.drawable.question10,
            "a. Drivers must keep their vehicles speed above 50 km/hr in the area ahead",
            "b. There is a speed limit of 50 km/hr in the area ahead",
            "c. Ending of a Speed limit zone",
            "d. There is a speed limit for longer vehicles usually 40m in length",
            2
        ))


        return questionsList
    }

}