package com.example.novascotiawrittendrivingtest

import com.example.novascotiawrittendrivingtest.apiCall.OpenAIInterface
import com.example.novascotiawrittendrivingtest.dataClass.ChatRequest
import com.example.novascotiawrittendrivingtest.dataClass.Message
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class OpenAIAPITest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: OpenAIInterface

    private val mockResponseJson = """
        {
            "choices": [
                {
                    "message": {
                        "content": "This is a mock response"
                    }
                }
            ]
        }
    """.trimIndent()

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenAIInterface::class.java)
    }

    @Test
    fun testPostChatData() {
        // Enqueue mock response
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(mockResponseJson)
        mockWebServer.enqueue(mockResponse)

        val messageForSystemRole = Message("system", "You are a helpful assistant for drive license test. You can answer questions about driving license test.")
        val messageForQuestion = Message("user", "What is the speed limit in a school zone?")

        // Create a list of messages to send to the API
        val sendMessageList : List<Message> = listOf(messageForSystemRole, messageForQuestion)

        // Make the API call
        val response = apiService.postChatData(ChatRequest(sendMessageList, "gpt-3.5-turbo-0613")).execute()

        // Assertions
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
        // Additional assertions based on the expected response

        // Assert that the request was as expected
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("POST /chat/completions HTTP/1.1", recordedRequest.requestLine)
        // Additional request assertions (headers, body, etc.)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}