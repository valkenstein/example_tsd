package com.aldredo.qrcode.data.api

import com.aldredo.qrcode.data.model.AuthorizationRequestModel
import com.aldredo.qrcode.data.model.stateRequest.ResponseAuthorizationModel
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationApi {
    @POST("auth/")
    suspend fun checkingAuthorizationData(@Body device_info: AuthorizationRequestModel): ResponseAuthorizationModel?
}