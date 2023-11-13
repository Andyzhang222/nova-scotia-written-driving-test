package com.example.novascotiawrittendrivingtest

data class User(
    val userId: String? = null,
    val currentQuestionPosition: Int = 0,
    val answerList: MutableMap<Int, String> = mutableMapOf()
)