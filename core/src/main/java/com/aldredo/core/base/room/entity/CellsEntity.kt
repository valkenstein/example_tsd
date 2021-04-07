package com.aldredo.core.base.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = arrayOf("barcode"), unique = true)])
data class CellsEntity(
    @PrimaryKey()
    val barcode: String = "",
    val type: String = "",
    val count_series: String = "",
    val count_goods: String = "",
    val name: String = ""
)