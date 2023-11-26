package com.example.novascotiawrittendrivingtest.apiCall.dataClass

import com.google.gson.annotations.SerializedName

data class Choices (
    @SerializedName("index"         ) val index        : Int?     ,
    @SerializedName("message"       ) val message      : Message?
)
