package com.example.novascotiawrittendrivingtest.apiCall.dataClass

import com.example.novascotiawrittendrivingtest.apiCall.dataClass.Choices
import com.google.gson.annotations.SerializedName

data class OpenAIResponse (
    @SerializedName("choices")
    var choices: ArrayList<Choices>?
)