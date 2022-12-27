package com.example.chatgbtexample.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequestBody (
    val model: String = "text-davinci-003",
    val prompt: String,
    val temperature: Int = 0,
    val max_tokens: Int = 2000,
)