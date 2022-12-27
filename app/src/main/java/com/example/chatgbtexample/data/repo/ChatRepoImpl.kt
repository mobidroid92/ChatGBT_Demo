package com.example.chatgbtexample.data.repo

import com.example.chatgbtexample.domain.repo.ChatRepo
import com.example.chatgbtexample.domain.api.ChatService
import javax.inject.Inject

class ChatRepoImpl @Inject constructor (
    private val chatService: ChatService
) : ChatRepo {

    override suspend fun sendMsg(msg: String): Result<String> {
        return chatService.sendMsg(msg)
    }

}