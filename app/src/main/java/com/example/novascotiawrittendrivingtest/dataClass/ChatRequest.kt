package com.example.novascotiawrittendrivingtest.dataClass

/**
 * Data class for chat request
 */
data class ChatRequest(
    val messages : List<Message>,
    val model : String
)
