package com.example.chatgbtexample.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.chatgbtexample.common.delegates.binding.BindingDelegate
import com.example.chatgbtexample.common.delegates.binding.BindingDelegateImpl

abstract class BaseActivity <Binding: ViewBinding> :
    AppCompatActivity() ,
    BindingDelegate<Binding> by BindingDelegateImpl() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding(this)
        setContentView(binding.root)

        setup(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyBinding()
    }

    abstract fun setup(savedInstanceState: Bundle?)
}