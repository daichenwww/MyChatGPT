package com.example.api_practice

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    val authHeader = "Bearer $YOUR_API_KEY"
    val chatMessages = mutableStateListOf<Message>()
    val imageMessage = mutableStateListOf<ImgMessage>()
    fun sendMessage(prompt: String) {
        Log.d("---send---", "--- send a message ---")
        chatMessages.add(Message("user", prompt))

        viewModelScope.launch {
            try {
                val response = ApiService.openAIApi.generateResponse(
                    authHeader = authHeader,
                    Request(
                        messages = chatMessages
                    )
                )
                chatMessages.add(response.choices.first().message)
            } catch (e: Exception) {
                Log.d("---debug---", "Error: ${e.message}")
            }
        }

    }
    fun sendMessageImg(prompt: String) {
        Log.d("---send---", "--- send a message ---")
        imageMessage.add(ImgMessage(prompt))

        viewModelScope.launch {
            try {
                val response = ApiService.openAIApi.generateImage(
                    authHeader = authHeader,
                    ImgRequest(
                        prompt = prompt,
                    )
                )
                imageMessage.add(response.data.first())
            } catch (e: Exception) {
                Log.d("---debug---", "Error: ${e.message}")
            }
        }

    }
}
