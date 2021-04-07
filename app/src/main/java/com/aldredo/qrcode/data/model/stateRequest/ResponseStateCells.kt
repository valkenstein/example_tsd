package com.aldredo.qrcode.data.model.stateRequest

import com.aldredo.qrcode.data.model.CellsResponse

sealed class ResponseStateCells {
    data class Error(val message: String?) : ResponseStateCells()
    data class Result(val result: CellsResponse) : ResponseStateCells()
    object EmptyResponse : ResponseStateCells()
}
