package com.example.chatgbtexample.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.example.chatgbtexample.common.delegates.binding.BindingDelegate
import com.example.chatgbtexample.common.delegates.binding.BindingDelegateImpl
import kotlinx.coroutines.launch

typealias DoObserve = suspend () -> Unit

abstract class BaseActivity <Binding: ViewBinding> :
    AppCompatActivity() ,
    BindingDelegate<Binding> by BindingDelegateImpl() {

    private val observersList = mutableListOf<DoObserve>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding(this)
        setContentView(binding.root)

        setupViews(savedInstanceState)
        listeners()
        addObservers(observersList)
        observerTheObservers()
    }

    private fun observerTheObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                observersList.forEach { itemToBeObserved ->
                    launch { itemToBeObserved.invoke() }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyBinding()
    }

    protected abstract fun setupViews(savedInstanceState: Bundle?)

    protected abstract fun listeners()

    protected abstract fun addObservers(observeList: MutableList<DoObserve>)

}