package com.aldredo.qrcode.data.model.stateRequest

import android.widget.Adapter
import com.aldredo.qrcode.data.model.NomenclatureModel
import com.aldredo.qrcode.data.model.SerialModel

sealed class ResponseStateNomenclature {
    data class Error(val message: String?) : ResponseStateNomenclature()
    data class Result(val result: ArrayList<NomenclatureModel>) : ResponseStateNomenclature()
    object EmptyResponse : ResponseStateNomenclature()
}

sealed class ResponseStateNomenclatureSeries {
    data class Error(val message: String?) : ResponseStateNomenclatureSeries()
    data class Result(val result: ArrayList<SerialModel>) : ResponseStateNomenclatureSeries()
    object EmptyResponse : ResponseStateNomenclatureSeries()
}