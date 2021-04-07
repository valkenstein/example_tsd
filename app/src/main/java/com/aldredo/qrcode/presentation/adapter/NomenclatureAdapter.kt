package com.aldredo.qrcode.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aldredo.core.base.adapter.BaseListAdapter
import com.aldredo.qrcode.R
import com.aldredo.qrcode.data.model.NomenclatureModel
import com.aldredo.qrcode.presentation.holder.NomenclatureHolder

class NomenclatureAdapter: BaseListAdapter<NomenclatureModel>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflateView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_nomenclature, parent, false)
        return NomenclatureHolder(inflateView, currentList)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NomenclatureModel>() {
            override fun areItemsTheSame(oldItem: NomenclatureModel, newItem: NomenclatureModel)
            = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: NomenclatureModel, newItem: NomenclatureModel)
            = oldItem == newItem
        }
    }
}