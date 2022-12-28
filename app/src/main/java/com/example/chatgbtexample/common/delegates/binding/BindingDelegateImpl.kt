package com.example.chatgbtexample.common.delegates.binding

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

class BindingDelegateImpl<Binding: ViewBinding> : BindingDelegate<Binding> {

    private var _binding: Binding? = null

    override val binding: Binding get() = _binding!!

    override fun initBinding(activity: AppCompatActivity) {
        if(_binding == null) {
            val type = activity.javaClass.genericSuperclass
            val clazz = (type as? ParameterizedType)?.actualTypeArguments?.get(0) as? Class<Binding>
            val method = clazz?.getMethod("inflate", LayoutInflater::class.java)
            _binding =  method?.invoke(null, activity.layoutInflater) as Binding
        }
    }

    override fun destroyBinding() {
        _binding = null
    }

}