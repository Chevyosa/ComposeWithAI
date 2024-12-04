package com.chev.composewithai.data.model.chat_response

import com.chev.composewithai.data.model.choice.Choice

data class ChatResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val prompt: List<Any>,
    val choices: List<Choice>,
)
