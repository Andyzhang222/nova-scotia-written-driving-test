package com.example.novascotiawrittendrivingtest.apiCall.dataClass

data class ChatRequest(
    val messages : List<Message>,
    val model : String
)
