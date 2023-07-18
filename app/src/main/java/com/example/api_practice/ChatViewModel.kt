package com.example.api_practice

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    val chatMessages = mutableStateListOf<Message>()
    fun sendMessage(text: String) {
        Log.d("---send---", "--- send a message ---")
        chatMessages.add(Message(text, "user"))

        viewModelScope.launch {
            try {
                val response = ApiService.openAIApi.generateResponse(
                    Request(
                        messages = chatMessages,
                    )
                )
                chatMessages.add(response.choices.first().message)
            } catch (e: Exception) {
                Log.d("---debug---", "Error: ${e.message}")
            }
        }

    }
}
