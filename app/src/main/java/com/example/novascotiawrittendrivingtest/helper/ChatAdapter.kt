package com.example.novascotiawrittendrivingtest.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.novascotiawrittendrivingtest.R
import com.example.novascotiawrittendrivingtest.dataClass.Message

class ChatAdapter(private val messageList: List<Message>) : RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

    /**
     * Creates new ViewHolder instances for each chat message item. This method is called by the RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val chatView = LayoutInflater.from(parent.context).inflate(R.layout.dialog_item, parent, false)
        return MyViewHolder(chatView)
    }

    /**
     * Binds the chat message data to the ViewHolder. This method is called by the RecyclerView.
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Get the current chat message
        val message = messageList[position]

        // Set the chat message content and visibility based on the role of the sender
        if (message.role == "user") {
            // If the sender is the user, set the user's chat message to the right and hide the AI's chat message
            holder.leftChatView.visibility = View.GONE
            holder.rightChatView.visibility = View.VISIBLE
            holder.rightTextView.text = message.content
        } else {
            // If the sender is the AI, set the AI's chat message to the left and hide the user's chat message
            holder.rightChatView.visibility = View.GONE
            holder.leftChatView.visibility = View.VISIBLE
            holder.leftTextView.text = message.content
        }
    }

    /**
     * Returns the number of chat messages in the list. This method is called by the RecyclerView.
     */
    override fun getItemCount(): Int {
        return messageList.size
    }

    /**
     * Adds a new chat message to the list and notifies the RecyclerView that the data has changed.
     */
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var leftChatView: LinearLayout = itemView.findViewById(R.id.left_chat_view)
        var rightChatView: LinearLayout = itemView.findViewById(R.id.right_chat_view)
        var leftTextView: TextView = itemView.findViewById(R.id.left_chat_text_view)
        var rightTextView: TextView = itemView.findViewById(R.id.right_chat_text_view)
    }
}
