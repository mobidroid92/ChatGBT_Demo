package com.example.chatgbtexample.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatgbtexample.presentation.model.MsgModel
import com.example.chatgbtexample.presentation.model.MsgType
import com.example.chatgbtexample.domain.useCase.SendMsgUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor (
    private val sendMsgUseCase: SendMsgUseCase
) : ViewModel() {

    private val _chatList = MutableStateFlow(listOf<MsgModel>())
    val chatList = _chatList.asStateFlow()

    private var msgToSend: String = ""

    private val _isSendButtonEnabled = MutableStateFlow(false)
    val isSendButtonEnabled = _isSendButtonEnabled.asStateFlow()

    fun updateText(msg: String) {
        msgToSend = msg
        _isSendButtonEnabled.update {
            msgToSend.isNotBlank() &&
                    (_chatList.value.isEmpty() ||_chatList.value.last().msgType != MsgType.Loading)
        }
    }

    fun sendMsg() {
        _chatList.update {
            it +
            MsgModel(text = msgToSend, msgType = MsgType.Me) +
            MsgModel(msgType = MsgType.Loading)
        }

        viewModelScope.launch {

            val sendMsgResult = sendMsgUseCase.execute(msgToSend)

            if(_chatList.value.last().msgType == MsgType.Loading) {
                _chatList.update { it.dropLast(1) }
            }

            if(sendMsgResult.isSuccess) {
                _chatList.update {
                    it + MsgModel(text = sendMsgResult.getOrNull()!!, msgType = MsgType.Other)
                }
            } else if(_chatList.value.last().msgType == MsgType.Me) {
                _chatList.update {
                    it.toMutableList().apply {
                        set(it.lastIndex, it.last().copy(isSuccess = false))
                    }
                }
            }
        }
        updateText("")
    }

}