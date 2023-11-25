package com.example.novascotiawrittendrivingtest.apiCall.dataClass

import com.example.novascotiawrittendrivingtest.apiCall.dataClass.Choices
import com.google.gson.annotations.SerializedName

/**
 * Data class for openAI response
 */
data class OpenAIResponse (
    @SerializedName("choices")
    var choices: ArrayList<Choices>?
)