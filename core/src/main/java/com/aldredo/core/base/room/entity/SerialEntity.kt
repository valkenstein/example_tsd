package com.aldredo.core.base.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SerialEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var series: String = "",
    var id_nomenclature: String = "",
    var storage_id: String = ""
)