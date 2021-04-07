package com.aldredo.qrcode.data.api

import com.aldredo.core.base.network.BodyRequest
import com.aldredo.qrcode.data.model.CellsResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface CellsApi {
    @POST("cells/")
    suspend fun getCells(@Body bodyRequest: BodyRequest): CellsResponse

    @POST("receiving_on_storage/put_cell/")
    suspend fun putCellsAndPallet(@Body bodyRequest: HashMap<Any, Any>): CellsResponse
}