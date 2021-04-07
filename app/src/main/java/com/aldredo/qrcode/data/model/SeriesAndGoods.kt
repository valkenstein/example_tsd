package com.aldredo.qrcode.data.model

import com.aldredo.core.base.room.entity.NomenclatureEntity
import com.aldredo.core.base.room.entity.SerialEntity

data class SeriesAndGoods(
    val nomenclatures: ArrayList<NomenclatureEntity>,
    val series: ArrayList<SerialEntity>
)