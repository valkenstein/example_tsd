package com.aldredo.core.base.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NomenclatureEntity(
    @PrimaryKey()
    var id: String = "",
    var name: String? = "",
    var id_nomenclature: String = "",
    var articul: String = "",
    var serial_id: String = ""
)

