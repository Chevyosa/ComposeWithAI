package com.chev.composewithai.data.model.chat_request

import com.chev.composewithai.data.model.message.Message

data class ChatRequest(
    val model: String = "Qwen/Qwen2.5-72B-Instruct-Turbo",
    val messages: List<Message>
)
