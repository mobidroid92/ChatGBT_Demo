package com.example.chatgbtexample.common.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter <T : Any> : RecyclerView.Adapter<BaseViewHolder>() {

    private var _context: Context? = null
    val context: Context get() = _context!!

    protected val layoutInflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }

    private val diffCallback = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return when (oldItem) {
                is Int, Long, Float, Double, Boolean, String -> oldItem == newItem
                is Model<*> -> (oldItem as Model<*>).id == (newItem as Model<*>).id
                else -> throw Exception("BaseRecyclerAdapter generic type should be Primitive, String, or child of Model")
            }
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    private val itemsList : List<T>
        get() = differ.currentList

    fun updateList(newList: List<T>?, runnable: Runnable? = null) {
        differ.submitList(newList, runnable)
    }

    fun getItem(position: Int): T {
        return itemsList[position]
    }

    override fun onBindViewHolder(baseViewHolder: BaseViewHolder, position: Int) {
        baseViewHolder.bindData(position)
    }

    override fun getItemCount(): Int = itemsList.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        _context = recyclerView.context
    }

}