package com.example.novascotiawrittendrivingtest.dataClass

import com.google.gson.annotations.SerializedName

/**
 * Data class for choices
 */
data class Choices (
    @SerializedName("index"         ) val index        : Int?     ,
    @SerializedName("message"       ) val message      : Message?
)
