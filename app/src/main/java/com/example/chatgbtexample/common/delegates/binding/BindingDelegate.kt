package com.example.chatgbtexample.common.delegates.binding

import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

interface BindingDelegate <Binding: ViewBinding> {

    val binding: Binding

    fun initBinding(activity: AppCompatActivity)

    fun destroyBinding()

}