package com.aldredo.qrcode.data.model.stateRequest


sealed class ResponseStateAuthorization {
    data class Error(val message: String?) : ResponseStateAuthorization()
    data class Result(val result: ResponseAuthorizationModel): ResponseStateAuthorization()
    object EmptyResponse: ResponseStateAuthorization()
}
