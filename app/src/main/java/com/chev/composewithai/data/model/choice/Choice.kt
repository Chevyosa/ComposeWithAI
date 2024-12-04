package com.chev.composewithai.data.model.choice

import com.chev.composewithai.data.model.message.Message

data class Choice(
    val finish_reason: String,
    val seed: String,
    val logprobs: Any?,
    val index: Int,
    val message: Message
)
