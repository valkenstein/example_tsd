package com.aldredo.qrcode.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aldredo.core.base.adapter.BaseListAdapter
import com.aldredo.qrcode.R
import com.aldredo.qrcode.data.model.SerialModel
import com.aldredo.qrcode.di.TaskWindowScope
import com.aldredo.qrcode.presentation.holder.MoveToCellHolder
import com.aldredo.qrcode.presentation.presenter.MoveToCellViewModel
import javax.inject.Inject

@TaskWindowScope
class MoveToCellAdapter @Inject constructor(private val viewModel: MoveToCellViewModel) :
    BaseListAdapter<SerialModel>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MoveToCellHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_nomenclature, parent, false), currentList, viewModel
        )
    }

    fun size() = currentList.size

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SerialModel>() {
            override fun areItemsTheSame(oldItem: SerialModel, newItem: SerialModel) =
                oldItem.series == newItem.series

            override fun areContentsTheSame(oldItem: SerialModel, newItem: SerialModel) =
                oldItem == newItem
        }
    }
}