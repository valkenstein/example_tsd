package com.aldredo.core.base.adapter

import androidx.recyclerview.widget.RecyclerView
import com.aldredo.core.base.holder.BaseHolder

abstract class BaseRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BaseHolder).bind()
    }
}