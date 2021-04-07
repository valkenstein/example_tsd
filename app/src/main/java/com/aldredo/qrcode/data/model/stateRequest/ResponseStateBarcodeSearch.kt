package com.aldredo.qrcode.data.model.stateRequest

import com.aldredo.qrcode.data.model.BarcodeModel
import com.aldredo.qrcode.data.model.CellsResponse

sealed class ResponseStateBarcodeSearch {
    data class Error(val message: String?) : ResponseStateBarcodeSearch()
    data class Result(val result: BarcodeModel) : ResponseStateBarcodeSearch()
    object EmptyResponse : ResponseStateBarcodeSearch()
}
