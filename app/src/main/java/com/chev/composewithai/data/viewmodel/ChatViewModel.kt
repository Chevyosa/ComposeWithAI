package com.chev.composewithai.data.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chev.composewithai.data.model.chat_request.ChatRequest
import com.chev.composewithai.data.model.chat_response.ChatResponse
import com.chev.composewithai.data.model.message.Message
import com.chev.composewithai.data.network.retrofit.RetrofitInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel : ViewModel() {
    private val _allMessages = mutableStateListOf<Message>()
    val allMessages: List<Message> get() = _allMessages

    private val _userMessages = mutableStateListOf<Message>() // Pesan dari user
    val userMessages: List<Message> = _userMessages

    private val _assistantMessages = mutableStateListOf<Message>() // Pesan dari assistant
    val assistantMessages: List<Message> = _assistantMessages

    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading.value

    fun sendMessage(userMessage: String) {
        if (userMessage.isBlank()) return
        val userChat = Message(role = "user", content = userMessage)
        _allMessages.add(userChat)

        _isLoading.value = true

        fetchChatResponse(userMessage)
    }

    private fun fetchChatResponse(userMessage: String) {
        val messages = listOf(Message(role = "user", content = userMessage))
        val request = ChatRequest(
            model = "Qwen/Qwen2.5-72B-Instruct-Turbo",
            messages = messages
        )

        val service = RetrofitInstance.api
        val call = service.getChatResponse("Bearer f21f8326bc84de1e25027b67cad1ee7680b9f71349daa6a274f837b2ef6f67b8", request)
        call.enqueue(object : Callback<ChatResponse> {
            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val assistantMessage = response.body()?.choices?.firstOrNull()?.message
                    assistantMessage?.let {
                        _allMessages.add(Message(role = "assistant", content = it.content))
                    }
                    if (assistantMessage != null) {
                        _assistantMessages.add(assistantMessage)
                    } else {
                        _assistantMessages.add(
                            Message(role = "assistant", content = "No response received.")
                        )
                    }
                } else {
                    _assistantMessages.add(
                        Message(
                            role = "assistant",
                            content = "Error: ${response.errorBody()?.string()}"
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                _assistantMessages.add(Message(role = "assistant", content = "Failed: ${t.message}"))
            }
        })
    }
}
