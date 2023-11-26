package com.example.novascotiawrittendrivingtest.dataClass

import com.google.gson.annotations.SerializedName

/**
 * Data class for message
 */
data class Message (
    @SerializedName("role"    ) val role    : String?,
    @SerializedName("content" ) val content : String?
)