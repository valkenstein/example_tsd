package com.aldredo.qrcode.data.model.stateRequest

import com.aldredo.qrcode.data.model.UpdateVersionModel

sealed class ResponseStateUpdateApp {
    data class Error(val message: String?) : ResponseStateUpdateApp()
    data class Result(val result: UpdateVersionModel) : ResponseStateUpdateApp()
}