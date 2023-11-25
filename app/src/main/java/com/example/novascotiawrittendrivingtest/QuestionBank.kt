package com.example.novascotiawrittendrivingtest

/*
    Object to store and manage a bank of questions for a driving test
 */
object QuestionBank {

    // ArrayList to store all the questions
    val questionsList = ArrayList<Question>()


    init {
        // Add various questions to the question list, each with its own details
        // Each question includes an ID, question text, image resource, options, and the correct answer index
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

        questionsList.add(Question(
            11,
            "What does this sign mean?",
            R.drawable.question11,
            "Snowmobile may use this road only in daylight",
            "Snowmobile is prohibited in this area",
            "Snowmobile may use this road",
            "Snowmobile training institute ahead",
            3
        ))

        questionsList.add(Question(
            12,
            "What does this sign mean?",
            R.drawable.question12,
            "This Road contain one-way traffic",
            "This Road contain two-way traffic",
            "Drivers must keep on left lane",
            "There is an intersection ahead",
            2
        ))

        questionsList.add(Question(
            13,
            "What does this sign mean?",
            R.drawable.question13,
            "Drivers cannot park their vehicles between these signs",
            "Drivers must not stop in the area between these signs",
            "Ongoing Construction in the area between these signs",
            "Drivers must not speed-up in the area between these signs",
            2
        ))

        questionsList.add(Question(
            14,
            "What does this sign mean?",
            R.drawable.question14,
            "This Road contain one-way traffic",
            "This Road contain two-way traffic",
            "Drivers must keep on left lane",
            "There is an intersection ahead",
            3
        ))

        questionsList.add(Question(
            15,
            "What does this sign mean?",
            R.drawable.question15,
            "There is Hidden intersection ahead",
            "There is a dead-end ahead",
            "There is a bridge ahead",
            "The pavement narrows ahead",
            4
        ))

        questionsList.add(Question(
            16,
            "What does this sign mean?",
            R.drawable.question16,
            "Drivers should stop for school bus when signals are flashing",
            "Drivers can pass through school bus with caution when signals are flashing",
            "Drivers must slow down their vehicle when signals are flashing",
            "Drivers must speed up their vehicles when signals are flashing",
            1
        ))

        questionsList.add(Question(
            17,
            "What does this sign mean?",
            R.drawable.question17,
            "Faster vehicles can pass through slower vehicles though right lane",
            "Faster traffic must keep on right side",
            "Slower traffic must keep on right side",
            "All traffic must keep on the right lane",
            3
        ))

        questionsList.add(Question(
            18,
            "What does this sign mean?",
            R.drawable.question18,
            "This Road is shared between Motorcars and bicycles",
            "The Motorcars and cycles are prohibited in this road",
            "The Cyclist must keep on Left lane",
            "The Motorcar drivers can drive recklessly",
            1
        ))

        questionsList.add(Question(
            19,
            "What does this sign mean?",
            R.drawable.question19,
            "There is U-turn ahead",
            "Roundabout ahead, Drivers must travel in clockwise direction",
            "Roundabout ahead, Drivers must travel in counter-clockwise direction",
            "There is a Construction zone ahead",
            3
        ))

        questionsList.add(Question(
            20,
            "What does this sign mean?",
            R.drawable.question20,
            "There is a dead-end ahead",
            "Drivers must come to a complete stop",
            "The Paved surface starts ahead",
            "The Paved surface ends ahead",
            4
        ))

        questionsList.add(Question(
            21,
            "What does this sign mean?",
            R.drawable.question21,
            "There is a traffic light ahead",
            "There is a stop sign ahead",
            "There is an intersection ahead",
            "There is a railway track ahead",
            1
        ))

        questionsList.add(Question(
            22,
            "What does this sign mean?",
            R.drawable.question22,
            "Danger close to the left sided lane",
            "Danger close to the right sided lane",
            "The Parking area",
            "There is a speed-breaker ahead",
            1
        ))

        questionsList.add(Question(
            23,
            "What does this sign mean?",
            R.drawable.question23,
            "Parking for vehicles having accessible parking permit",
            "Parking is prohibited in this area",
            "Ambulance parking area",
            "Parking prohibited for disabled persons",
            1
        ))

        questionsList.add(Question(
            24,
            "What does this sign mean?",
            R.drawable.question24,
            "Do not enter this road",
            "Drivers must remain on right side of the road",
            "Drivers must come to a complete stop",
            "Drivers can’t park in this area",
            1
        ))

        questionsList.add(Question(
            25,
            "What does this sign mean?",
            R.drawable.question25,
            "Intersection ahead, Drivers from the mentioned direction has the right-of-way",
            "Intersection ahead, Drivers must go straight",
            "Intersection ahead, Drivers must turn left or right",
            "Intersection ahead, Drivers from the mentioned lane must yield the right-of-way to the other drivers",
            1
        ))

        questionsList.add(Question(
            26,
            "What does this sign mean?",
            R.drawable.question26,
            "There is a winding road ahead",
            "There maybe a land sliding ahead",
            "The road ahead is slippery when wet",
            "There is High Occupancy Lanes ahead",
            3
        ))

        questionsList.add(Question(
            27,
            "What does this sign mean?",
            R.drawable.question27,
            "There is a U-turn ahead",
            "There is a slight left turn ahead",
            "There is a separation of road ahead",
            "Two roads going in the same direction merging ahead",
            4
        ))

        questionsList.add(Question(
            28,
            "What does this sign mean?",
            R.drawable.question28,
            "Drivers cannot park in the area between these signs",
            "Drivers must not stop their vehicle in the area between these signs",
            "Drivers can park their vehicles in the area between these signs",
            "Bicycles can only park in the area between these signs, not four wheel vehicles",
            1
        ))

        questionsList.add(Question(
            29,
            "What does this sign mean?",
            R.drawable.question29,
            "The Safest maximum speed limit at the curve",
            "The Safest minimum speed limit at the curve",
            "The Speed limit in the area ahead",
            "None of the above",
            1
        ))

        questionsList.add(Question(
            30,
            "What does this sign mean?",
            R.drawable.question30,
            "Warning for school-zone ahead: Drive with caution and lookout for Children",
            "Warning for park-zone ahead: Drive with caution and lookout for Children",
            "Warning for Hospital-region ahead: Drive with caution and lookout for Patients",
            "Warning for Pedestrian-Crossing ahead: Drive with caution and lookout for pedestrians",
            1
        ))

        questionsList.add(Question(
            31,
            "What does this sign mean?",
            R.drawable.question31,
            "There is no U-turn ahead",
            "Drivers must take U-turn ahead",
            "Do not take U-Turn ahead",
            "Drivers must take left turn ahead",
            3
        ))

        questionsList.add(Question(
            32,
            "What does this sign mean?",
            R.drawable.question32,
            "Bicycles are prohibited in this area",
            "There is a bicycle parking ahead",
            "There is a bicycle crossing ahead",
            "This road contain an official bicycle-lane",
            3
        ))

        questionsList.add(Question(
            33,
            "What does this sign mean?",
            R.drawable.question33,
            "Drivers must take right turn when facing red light at intersection",
            "Drivers must not take right turn when facing red light at intersection",
            "Drivers can go straight when facing a red light at intersection",
            "Drivers must come to a complete stop when facing red light at intersection",
            2
        ))

        questionsList.add(Question(
            34,
            "What does this sign mean?",
            R.drawable.question34,
            "The road ahead may contain large vehicles",
            "There is a steep hill ahead",
            "The Parking area for large vehicles",
            "The beginning of Paved road",
            2
        ))

        questionsList.add(Question(
            35,
            "What does this sign mean?",
            R.drawable.question35,
            "There is a lifted bridge ahead",
            "There is a construction zone ahead",
            "There is a coastal area ahead",
            "There is a crosswalk ahead",
            1
        ))

        questionsList.add(Question(
            36,
            "What does this sign mean?",
            R.drawable.question36,
            "Drivers must take left turn other than the time mentioned",
            "Drivers must take left turn during the time mentioned",
            "Drivers must not take left turn during the time mentioned",
            "Drivers must not take left turn other than the time mentioned",
            3
        ))

        questionsList.add(Question(
            37,
            "What does this sign mean?",
            R.drawable.question37,
            "Drivers must not pass other vehicles in this road",
            "Drivers can pass other vehicles in this road",
            "There is a single-lane road ahead",
            "There is a two-lane road ahead",
            1
        ))

        questionsList.add(Question(
            38,
            "What does this sign mean?",
            R.drawable.question38,
            "There is a community zone ahead; Special risk of pedestrians in the area",
            "There is a construction zone ahead: Special risk of workers in the area",
            "There is Hospital zone ahead: Special risk of patients in the area",
            "There is police station ahead: Specials risk of pedestrians in the area",
            1
        ))

        questionsList.add(Question(
            39,
            "What does this sign mean?",
            R.drawable.question39,
            "Drivers cannot park their vehicle in the area between these signs",
            "Drivers must not stop their vehicle except while loading or unloading passengers in the area between these signs",
            "Drivers can speed-up in the area between these signs because of no pedestrian",
            "Pedestrians are not allowed in the area between these signs",
            2
        ))

        questionsList.add(Question(
            40,
            "What does this sign mean?",
            R.drawable.question40,
            "There is a sharp right turn ahead",
            "There is a sharp left turn ahead",
            "There is a slight right curve ahead",
            "There is a slight left curve ahead",
            3
        ))
    }

    /**
     * Function to return all questions
     */
    fun getAllQuestionsEN(): ArrayList<Question> {
        return questionsList
    }

    /**
     * Function to return a list of questions based on their IDs
     */
    fun getQuestionsByIds(questionIds: List<String>): ArrayList<Question> {
        // Convert String IDs to Int
        val intIds = questionIds.mapNotNull { it.toIntOrNull() }

        // Filter questions based on converted IDs
        return ArrayList(questionsList.filter { it.id in intIds })
    }
}