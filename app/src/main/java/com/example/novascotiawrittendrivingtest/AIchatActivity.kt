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

        recyclerView = findViewById(R.id.recycler_view)
        welcomeTextView = findViewById(R.id.welcome_text)
        messageEditText = findViewById(R.id.message_edit_text)
        sendButton = findViewById(R.id.send_btn)
        backButton = findViewById(R.id.backButton)


        // Setup recycler view
        messageAdapter = ChatAdapter(messageList)
        recyclerView.adapter = messageAdapter
        recyclerView.layoutManager = LinearLayoutManager(this).apply { stackFromEnd = true }

        sendButton.setOnClickListener {
            val question = messageEditText.text.toString()
            addToChat(question, "user")
            messageEditText.text.clear()
            callAPI(question)
            welcomeTextView.visibility = View.GONE
        }

        backButton.setOnClickListener{
            navigateToMain()
        }
    }

    private fun addToChat(message: String, sentBy: String) {
        runOnUiThread {
            messageList.add(Message(sentBy, message))
            messageAdapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(messageAdapter.itemCount)
        }
    }

    private fun addResponse(response: String) {
        messageList.removeAt(messageList.size - 1)
        addToChat(response, "assistant")
    }

    private fun callAPI(question: String) {
        // okhttp
        messageList.add(Message("assistant ", "Typing... "))

        // Create Retrofit instance to call the API
        val retrofit = ClientBuilder.buildService(OpenAIInterface::class.java)

        val messageForSystemRole = Message("system", "You are a helpful assistant for drive license test. You can answer questions about driving license test.")
        val messageForQuestion = Message("user", question)

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
                                addResponse("No response available")
                                return
                            }

                            addResponse(responseMessage)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    } else {
                        addResponse("API call unsuccessful: ${response.errorBody()?.string()}")
                    }
                }

                // If there is an error, show the error message
                override fun onFailure(call: Call<OpenAIResponse>, t: Throwable) {
                    addResponse("Failed to load response due to " + t.message)
                }
            })
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        // Add other extras as needed
        startActivity(intent)
    }
}