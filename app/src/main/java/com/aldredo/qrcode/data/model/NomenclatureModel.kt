package com.aldredo.qrcode.data.model

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import retrofit2.converter.gson.GsonConverterFactory

data class NomenclatureModel(
    val id: String?,
    val articul: String?,
    val serial: List<String>?,
    val name: String?,
) {
    data class StorageModel(
        val storage_id: String?,
        //  val series: List<String>?
    )
}