package com.aldredo.qrcode.data.api

import com.aldredo.qrcode.data.model.NomenclatureModel
import retrofit2.http.Body
import retrofit2.http.POST

interface NomenclatureApi {
    @POST("goods/")
    suspend fun getListNomenclature(@Body bodyRequest: HashMap<Any,Any>): ArrayList<NomenclatureModel>
}