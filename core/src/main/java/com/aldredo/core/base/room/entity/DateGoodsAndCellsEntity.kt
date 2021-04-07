package com.aldredo.core.base.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class DateGoodsAndCellsEntity(
    @PrimaryKey()
    val id: String = "",
    val context: String = "",
    val date: Long = 0,
) {
    companion object {
        const val CELLS_ID = "CELLS"
        const val GOODS_ID = "GOODS"
    }
}