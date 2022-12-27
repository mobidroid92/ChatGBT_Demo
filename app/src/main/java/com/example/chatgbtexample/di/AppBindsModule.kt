package com.example.chatgbtexample.di

import com.example.chatgbtexample.data.repo.ChatRepoImpl
import com.example.chatgbtexample.data.api.ChatServiceImpl
import com.example.chatgbtexample.domain.repo.ChatRepo
import com.example.chatgbtexample.domain.api.ChatService
import com.example.chatgbtexample.domain.useCase.SendMsgUseCase
import com.example.chatgbtexample.domain.useCase.SendMsgUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindsModule {

    @Binds
    abstract fun bindChatService(chatServiceImpl: ChatServiceImpl): ChatService

    @Binds
    abstract fun bindChatRepo(chatRepoImpl: ChatRepoImpl) : ChatRepo

    @Binds
    abstract fun bindSendMsgUseCase(sendMsgUseCaseImpl: SendMsgUseCaseImpl): SendMsgUseCase

}