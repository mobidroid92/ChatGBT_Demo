package com.example.chatgbtexample.domain.useCase

interface SendMsgUseCase {

    suspend fun execute(msg: String): Result<String>

}