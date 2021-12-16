package com.aslansari.notes.ui.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected val items: MutableList<T> = mutableListOf()
    protected var OnItemClickListener: ((T) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createItemViewHolder(parent = parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bindItemViewHolder(viewHolder = holder, position = position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    abstract fun createItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    abstract fun bindItemViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int)

    fun getItem(position: Int): T {
        return items[position]
    }

    fun add(item: T) {
        items.add(item)
        this.notifyItemInserted(itemCount - 1)
    }

    fun addAll(items: List<T>) {
        items.forEach { add(it) }
    }

    fun remove(item: T) {
        val position = items.indexOf(item)
        if (position > -1) {
            items.remove(item)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }
}