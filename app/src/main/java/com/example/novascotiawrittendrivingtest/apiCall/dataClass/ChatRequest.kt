package com.example.novascotiawrittendrivingtest.apiCall.dataClass

/**
 * Data class for chat request
 */
data class ChatRequest(
    val messages : List<Message>,
    val model : String
)
