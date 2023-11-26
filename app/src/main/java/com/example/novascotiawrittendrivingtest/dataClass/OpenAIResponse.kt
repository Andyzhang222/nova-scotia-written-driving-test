package com.example.novascotiawrittendrivingtest.dataClass

import com.example.novascotiawrittendrivingtest.dataClass.Choices
import com.google.gson.annotations.SerializedName

/**
 * Data class for openAI response
 */
data class OpenAIResponse (
    @SerializedName("choices")
    var choices: ArrayList<Choices>?
)