package com.example.novascotiawrittendrivingtest

data class Question(
    val questionText: String,
    val options: List<String>,
    val imageResId: Int,
    val correctAnswer: String
)
