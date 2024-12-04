package com.chev.composewithai.data.network.api

import com.chev.composewithai.data.model.chat_request.ChatRequest
import com.chev.composewithai.data.model.chat_response.ChatResponse
import com.chev.composewithai.data.network.response.NetworkResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AIApi {
    @POST("v1/chat/completions")
    @Headers("Accept: application/json", "Content-Type: application/json")
    fun getChatResponse(
        @Header("Authorization") authorization: String,
        @Body request: ChatRequest
    ): Call<ChatResponse>
}
