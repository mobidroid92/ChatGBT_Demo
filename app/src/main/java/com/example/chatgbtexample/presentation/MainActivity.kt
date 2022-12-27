package com.example.chatgbtexample.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatgbtexample.R
import com.example.chatgbtexample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var chatRecyclerAdapter: ChatRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}