package com.example.novascotiawrittendrivingtest.apiCall

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientBuilder {
    // Create an instance of OkHttpClient.
    private val client = OkHttpClient.Builder().build()

    // Initialize a Retrofit builder.
    private val retrofit = Retrofit.Builder()
        // Set the base URL for the web service.
        .baseUrl("https://api.openai.com/v1/")
        // Add GsonConverterFactory to parse JSON responses into Kotlin objects using Gson.
        .addConverterFactory(GsonConverterFactory.create())
        // Attach the OkHttpClient to Retrofit.
        .client(client)
        // Build the Retrofit instance.
        .build()

    /**
     * Generic function to create a service interface implementation.
     */
    fun <T> buildService(service: Class<T>): T {
        // Create and return the Retrofit service from the service interface class.
        return retrofit.create(service)
    }
}