package com.example.chatgbtexample.domain.api

interface ChatService {

    suspend fun sendMsg(msg: String): Result<String>

}