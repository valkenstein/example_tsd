package com.aldredo.core.base.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHolder(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind()
}