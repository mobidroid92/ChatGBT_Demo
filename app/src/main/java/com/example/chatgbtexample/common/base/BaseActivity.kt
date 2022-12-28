package com.example.chatgbtexample.common.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.chatgbtexample.common.delegates.binding.BindingDelegate
import com.example.chatgbtexample.common.delegates.binding.BindingDelegateImpl

open class BaseActivity <Binding: ViewBinding> :
    AppCompatActivity() ,
    BindingDelegate<Binding> by BindingDelegateImpl() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        initBinding(this)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyBinding()
    }
}