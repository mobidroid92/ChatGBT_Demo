package com.example.chatgbtexample.presentation

import android.view.ViewGroup
import androidx.core.view.isVisible
import coil.load
import com.example.chatgbtexample.R
import com.example.chatgbtexample.common.base.BaseRecyclerAdapter
import com.example.chatgbtexample.common.base.BaseViewHolder
import com.example.chatgbtexample.databinding.RecyclerItemLoadingBinding
import com.example.chatgbtexample.databinding.RecyclerItemMeBinding
import com.example.chatgbtexample.databinding.RecyclerItemOtherBinding
import com.example.chatgbtexample.presentation.model.MsgModel
import com.example.chatgbtexample.presentation.model.MsgType
import com.example.chatgbtexample.presentation.model.MsgType.*
import com.example.chatgbtexample.common.toTime

class ChatRecyclerAdapter : BaseRecyclerAdapter<MsgModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when(MsgType.values()[viewType]) {
            Me -> ChatsByMeViewHolder(
                RecyclerItemMeBinding.inflate(layoutInflater, parent, false)
            )
            Loading -> LoadingViewHolder(
                RecyclerItemLoadingBinding.inflate(layoutInflater, parent, false)
            )
            Other -> ChatsByOtherViewHolder(
                RecyclerItemOtherBinding.inflate(layoutInflater, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).msgType.ordinal
    }

    private inner class ChatsByMeViewHolder(
        private val binding: RecyclerItemMeBinding
    ): BaseViewHolder(binding) {

        private var item: MsgModel? = null

        override fun bindData(position: Int) {
            item = getItem(position)
            binding.msgTextView.text = item?.text
            binding.errorTextView.isVisible = item?.isSuccess == false
            binding.timeTextView.text = item?.created?.toTime()
        }
    }

    private inner class ChatsByOtherViewHolder(
        private val binding: RecyclerItemOtherBinding
    ): BaseViewHolder(binding) {

        private var item: MsgModel? = null

        override fun bindData(position: Int) {
            item = getItem(position)
            binding.msgTextView.text = item?.text
            binding.timeTextView.text = item?.created?.toTime()
        }
    }

    private inner class LoadingViewHolder(
        private val binding: RecyclerItemLoadingBinding
    ) : BaseViewHolder(binding) {

        override fun bindData(position: Int) {
            binding.loadingImage.load(R.drawable.typing)
        }
    }
}