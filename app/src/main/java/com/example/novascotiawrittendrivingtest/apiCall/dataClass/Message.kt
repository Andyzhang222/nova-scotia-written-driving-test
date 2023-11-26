package com.example.novascotiawrittendrivingtest.apiCall.dataClass

import com.google.gson.annotations.SerializedName

data class Message (
    @SerializedName("role"    ) val role    : String?,
    @SerializedName("content" ) val content : String?
)