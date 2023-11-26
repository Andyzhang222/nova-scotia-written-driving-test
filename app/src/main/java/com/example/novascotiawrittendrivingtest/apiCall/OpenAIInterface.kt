package com.example.novascotiawrittendrivingtest.apiCall

import com.example.novascotiawrittendrivingtest.dataClass.ChatRequest
import com.example.novascotiawrittendrivingtest.dataClass.OpenAIResponse
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header

private const val AIP_KEY : String = "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"

interface OpenAIInterface {
    @POST("chat/completions")
    fun postChatData(
        @Body chatRequest: ChatRequest,
        @Header ("Content-Type") contentType: String = "application/json; charset=utf-8",
        @Header ("Authorization") authorization: String = "Bearer $AIP_KEY"
    ): Call<OpenAIResponse>
}