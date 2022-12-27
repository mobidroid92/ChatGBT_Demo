package com.example.chatgbtexample.domain.repo

interface ChatRepo {

    suspend fun sendMsg(msg: String): Result<String>

}