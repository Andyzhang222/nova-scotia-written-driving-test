package com.example.novascotiawrittendrivingtest

/**
 * User data class for storing user information
 */
data class User(
    val userId: String? = null,
    val currentQuestionPosition: Int = 0,
    val currentPositionInWrongQuestion: Int = 0,
    val answerList: MutableMap<Int, String> = mutableMapOf()
)