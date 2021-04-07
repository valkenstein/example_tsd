package com.aldredo.qrcode.presentation.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.aldredo.core.base.holder.BaseHolder
import com.aldredo.qrcode.R
import com.aldredo.qrcode.data.model.SerialModel
import com.aldredo.qrcode.presentation.presenter.MoveToCellViewModel

class MoveToCellHolder(
    val view: View,
    private val currentList: MutableList<SerialModel>,
    viewModel: MoveToCellViewModel
) :
    BaseHolder(view) {
    var series: TextView? = null
    var name: TextView? = null
    var delete: ImageView? = null

    init {
        series = view.findViewById(R.id.title)
        name = view.findViewById(R.id.name)
        delete = view.findViewById(R.id.delete)
        delete?.setOnClickListener {
            viewModel.deleteElement(currentList[bindingAdapterPosition])
        }
    }

    override fun bind() {
        series?.text = currentList[bindingAdapterPosition].series
        name?.text = currentList[bindingAdapterPosition].name
    }
}