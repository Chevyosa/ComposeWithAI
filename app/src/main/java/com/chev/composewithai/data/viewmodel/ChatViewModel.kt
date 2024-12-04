package com.chev.composewithai.data.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.chev.composewithai.data.model.chat_request.ChatRequest
import com.chev.composewithai.data.model.chat_response.ChatResponse
import com.chev.composewithai.data.model.message.Message
import com.chev.composewithai.data.network.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel : ViewModel() {
    private val _userMessages = mutableStateListOf<Message>() // Pesan dari user
    val userMessages: List<Message> = _userMessages

    private val _assistantMessages = mutableStateListOf<Message>() // Pesan dari assistant
    val assistantMessages: List<Message> = _assistantMessages

    fun fetchChatResponse(apiKey: String, userMessage: String) {
        val messages = listOf(Message(role = "user", content = userMessage))
        val request = ChatRequest(
            model = "Qwen/Qwen2.5-72B-Instruct-Turbo",
            messages = messages
        )

        val service = RetrofitInstance.api
        val call = service.getChatResponse("Bearer $apiKey", request)
        call.enqueue(object : Callback<ChatResponse> {
            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                if (response.isSuccessful) {
                    val assistantReply = response.body()?.choices?.firstOrNull()?.message?.content
                        ?: "No response content."

                    _userMessages.add(Message(role = "user", content = userMessage))
                    _assistantMessages.add(Message(role = "assistant", content = assistantReply))
                } else {
                    val errorMessage = "Error: ${response.errorBody()?.string()}"
                    _assistantMessages.add(Message(role = "assistant", content = errorMessage))
                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                _assistantMessages.add(Message(role = "assistant", content = "Failed: ${t.message}"))
            }
        })
    }
}
