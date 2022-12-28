package com.example.chatgbtexample.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatgbtexample.common.base.BaseActivity
import com.example.chatgbtexample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var chatRecyclerAdapter: ChatRecyclerAdapter

    override fun setup(savedInstanceState: Bundle?) {
        setupViews()
        listeners()
        observers()
    }

    private fun setupViews() {
        setSupportActionBar(binding.toolbar)
        setupRecycler()
    }

    private fun setupRecycler() {
        chatRecyclerAdapter = ChatRecyclerAdapter()
        //Need this to scroll to bottom correctly.
        (binding.chatsRecyclerView.layoutManager as? LinearLayoutManager)?.stackFromEnd = true
        binding.chatsRecyclerView.adapter = chatRecyclerAdapter

    }

    private fun listeners() {
        binding.sendMsgBtn.setOnClickListener {
            viewModel.sendMsg()
            binding.msgToSendEditText.setText("")
        }
        binding.msgToSendEditText.doOnTextChanged { text, start, before, count ->
            viewModel.updateText(binding.msgToSendEditText.text.toString())
        }
    }

    private fun observers() {
        lifecycleScope.launchWhenStarted {
            viewModel.chatList.collect {
                chatRecyclerAdapter.updateList(it) {
                    if(it.isNotEmpty()) {
                        binding.chatsRecyclerView.smoothScrollToPosition(it.lastIndex)
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.isSendButtonEnabled.collect {
                binding.sendMsgBtn.isEnabled = it
            }
        }
    }

}