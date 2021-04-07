package com.aldredo.qrcode.presentation.holder

import android.view.View
import android.widget.TextView
import com.aldredo.core.base.holder.BaseHolder
import com.aldredo.qrcode.R
import com.aldredo.qrcode.data.model.NomenclatureModel

class NomenclatureHolder(val view: View, private val currentList: MutableList<NomenclatureModel>) :
    BaseHolder(view) {
    override fun bind() {
        view.findViewById<TextView>(R.id.title).text = currentList[layoutPosition].name
    }
}