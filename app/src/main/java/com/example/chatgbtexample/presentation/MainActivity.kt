package com.example.chatgbtexample.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatgbtexample.common.base.BaseActivity
import com.example.chatgbtexample.common.base.DoObserve
import com.example.chatgbtexample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var chatRecyclerAdapter: ChatRecyclerAdapter

    override fun setupViews(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.toolbar)
        setupRecycler()
    }

    private fun setupRecycler() {
        chatRecyclerAdapter = ChatRecyclerAdapter()
        //Need this to scroll to bottom correctly.
        (binding.chatsRecyclerView.layoutManager as? LinearLayoutManager)?.stackFromEnd = true
        binding.chatsRecyclerView.adapter = chatRecyclerAdapter

    }

    override fun listeners() {
        binding.sendMsgBtn.setOnClickListener {
            viewModel.sendMsg()
            binding.msgToSendEditText.setText("")
        }
        binding.msgToSendEditText.doOnTextChanged { text, start, before, count ->
            viewModel.updateText(binding.msgToSendEditText.text.toString())
        }
    }

     override fun addObservers(observeList: MutableList<DoObserve>) {
         observeList.add {
             viewModel.chatList.collect {
                 chatRecyclerAdapter.updateList(it) {
                     if(it.isNotEmpty()) {
                         binding.chatsRecyclerView.smoothScrollToPosition(it.lastIndex)
                     }
                 }
             }
         }
         observeList.add {
             viewModel.isSendButtonEnabled.collect {
                 binding.sendMsgBtn.isEnabled = it
             }
         }
    }

}