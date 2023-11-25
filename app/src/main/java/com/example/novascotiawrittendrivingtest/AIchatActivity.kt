package com.example.novascotiawrittendrivingtest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.novascotiawrittendrivingtest.apiCall.ClientBuilder
import com.example.novascotiawrittendrivingtest.apiCall.OpenAIInterface
import com.example.novascotiawrittendrivingtest.apiCall.dataClass.ChatRequest
import com.example.novascotiawrittendrivingtest.apiCall.dataClass.Message
import com.example.novascotiawrittendrivingtest.apiCall.dataClass.OpenAIResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AIchatActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var welcomeTextView: TextView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var backButton: ImageView
    private val messageList = mutableListOf<Message>()
    private lateinit var messageAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aichat)

        // Initialize views
        recyclerView = findViewById(R.id.recycler_view)
        welcomeTextView = findViewById(R.id.welcome_text)
        messageEditText = findViewById(R.id.message_edit_text)
        sendButton = findViewById(R.id.send_btn)
        backButton = findViewById(R.id.backButton)

        // Setup recycler view
        messageAdapter = ChatAdapter(messageList)
        recyclerView.adapter = messageAdapter
        recyclerView.layoutManager = LinearLayoutManager(this).apply { stackFromEnd = true }

        // Set send button listener
        sendButton.setOnClickListener {
            val question = messageEditText.text.toString()
            addToChat(question, "user")
            messageEditText.text.clear()
            callAPI(question)
            welcomeTextView.visibility = View.GONE
        }

        // Set back button listener
        backButton.setOnClickListener{
            navigateToMain()
        }
    }

    /**
     * Adds a new chat message to the list and notifies the RecyclerView that the data has changed.
     *
     * @param message The message to add to the chat
     * @param sentBy The role of the sender of the message
     */
    private fun addToChat(message: String, sentBy: String) {
        runOnUiThread {
            // Add the message to the chat
            messageList.add(Message(sentBy, message))

            // Notify the RecyclerView that the data has changed
            messageAdapter.notifyDataSetChanged()

            // Scroll to the bottom of the chat
            recyclerView.smoothScrollToPosition(messageAdapter.itemCount)
        }
    }

    // Add the response to the chat

    /**
     * Adds the response from the API to the chat.
     *
     * @param response The response from the API
     */
    private fun addResponse(response: String) {
        // Remove the typing message
        messageList.removeAt(messageList.size - 1)
        // Add the response to the chat
        addToChat(response, "assistant")
    }

    /**
     * Calls the OpenAI API to get a response to the user's question.
     *
     * @param question The user's question
     */
    private fun callAPI(question: String) {
        // Add a message to show that the assistant is typing
        messageList.add(Message("assistant ", "Typing... "))

        // Create Retrofit ins  tance to call the API
        val retrofit = ClientBuilder.buildService(OpenAIInterface::class.java)

        // Create messages to send to the API
        val messageForSystemRole = Message("system", "You are a helpful assistant for drive license test. You can answer questions about driving license test.")
        val messageForQuestion = Message("user", question)

        // Create a list of messages to send to the API
        val sendMessageList : List<Message> = listOf(messageForSystemRole, messageForQuestion)

        // Call the API
        retrofit.postChatData(ChatRequest(sendMessageList, "gpt-3.5-turbo-0613")).enqueue(object : Callback<OpenAIResponse> {
                // If the API response is successful, get the wanted data from API response
                override fun onResponse(
                    call: Call<OpenAIResponse>,
                    response: Response<OpenAIResponse>
                ) {
                    // Check if the API response is successful
                    if (response.isSuccessful) {
                        try {

                            // Get the forecast in API response
                            val responseMessage = response.body()?.choices?.get(0)?.message?.content
                            // Check if the forecast is null or empty
                            if (responseMessage.isNullOrEmpty()) {
                                // If the forecast is null or empty, show the error message
                                addResponse("No response available")
                                return
                            }

                            // If the forecast is not null or empty, show the response
                            addResponse(responseMessage)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    } else {
                        // If the API response is not successful, show the error message
                        addResponse("API call unsuccessful: ${response.errorBody()?.string()}")
                    }
                }

                // If there is an error, show the error message
                override fun onFailure(call: Call<OpenAIResponse>, t: Throwable) {
                    addResponse("Failed to load response due to " + t.message)
                }
            })
    }

    /**
     * Navigates to the main activity.

     */
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        // Add other extras as needed
        startActivity(intent)
        finish()
    }
}