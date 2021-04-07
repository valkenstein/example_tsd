package com.aldredo.qrcode.data.api

import com.aldredo.core.base.network.BodyRequest
import com.aldredo.qrcode.data.model.BarcodeModel
import com.aldredo.qrcode.data.model.CellsResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface BarcodeTypeApi {
    @POST("receiving_on_storage/get_pallet/")
    suspend fun verifyBarcode(@Body bodyRequest: BodyRequest): BarcodeModel
}