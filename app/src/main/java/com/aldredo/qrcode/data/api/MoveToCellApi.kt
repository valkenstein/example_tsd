package com.aldredo.qrcode.data.api

import com.aldredo.core.base.network.BodyRequest
import com.aldredo.qrcode.data.model.MoveToCellResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MoveToCellApi {
    @POST("movetocell/")
    suspend fun saveBarcodeToServer(@Body bodyRequest: HashMap<Any, Any>): MoveToCellResponse
}