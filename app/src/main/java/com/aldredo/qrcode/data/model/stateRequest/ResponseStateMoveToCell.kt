package com.aldredo.qrcode.data.model.stateRequest

import com.aldredo.qrcode.data.model.MoveToCellResponse

sealed class ResponseStateMoveToCell{
    data class Error(val message: String?) : ResponseStateMoveToCell()
    data class Result(val result: MoveToCellResponse): ResponseStateMoveToCell()
    object EmptyResponse: ResponseStateMoveToCell()
}
