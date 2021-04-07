package com.aldredo.qrcode.data.repository

import android.util.Log
import com.aldredo.core.base.room.AppDatabase
import com.aldredo.core.base.room.entity.DateGoodsAndCellsEntity
import com.aldredo.core.base.util.IManagerInfoDevice
import com.aldredo.qrcode.data.api.NomenclatureApi
import com.aldredo.qrcode.data.model.NomenclatureModel
import com.aldredo.qrcode.data.model.SerialModel
import com.aldredo.qrcode.data.model.stateRequest.ResponseStateNomenclature
import com.aldredo.qrcode.data.model.stateRequest.ResponseStateNomenclatureSeries
import com.google.gson.Gson
import retrofit2.Retrofit
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class NomenclatureRepository @Inject constructor(
    private val retrofit: Retrofit,
    private val bd: AppDatabase,
    private val manager: IManagerInfoDevice
) {
    suspend fun getListNomenclature(list: List<SerialModel>): ResponseStateNomenclatureSeries {
        val hashMap = HashMap<Any, Any>()
        return try {
            hashMap["filter"] = HashMap<String, List<String>>().apply {
                put(
                    "serial", mappingToString(list)
                )
            }
            val nomenclature =
                retrofit.create(NomenclatureApi::class.java).getListNomenclature(hashMap)
            if (nomenclature.isEmpty())
                ResponseStateNomenclatureSeries.EmptyResponse
            else
                ResponseStateNomenclatureSeries.Result(mappingToSerial(nomenclature))
        } catch (e: Exception) {
            ResponseStateNomenclatureSeries.Error(e.message)
        }
    }

    private fun mappingToSerial(list: List<NomenclatureModel>): ArrayList<SerialModel> {
        return ArrayList<SerialModel>().apply {
            list.forEach {
                val series = it.serial?.get(0)
                add(SerialModel(name = it.name ?: "-", series = series ?: "-"))
            }
        }
    }

    private fun mappingToString(list: List<SerialModel>): ArrayList<String> {
        return ArrayList<String>().apply {
            list.forEach {
                add(it.series)
            }
        }
    }

    suspend fun getListNomenclature(): ResponseStateNomenclature {
        val hashMap = HashMap<Any, Any>()
        return try {
            if (checkCashTime()) {
                ResponseStateNomenclature.Error("данные номенкулатуры актуальны")
            } else {
                hashMap["filter"] = HashMap<String, List<String>>().apply {
                    put(
                        "storage",
                        listOf(body.storage_id)
                    )
                }
                val nomenclature =
                    retrofit.create(NomenclatureApi::class.java).getListNomenclature(hashMap)
                if (nomenclature.isEmpty())
                    ResponseStateNomenclature.EmptyResponse
                else
                    ResponseStateNomenclature.Result(nomenclature)
            }
        } catch (e: Exception) {
            ResponseStateNomenclature.Error(e.message)
        }
    }

    private fun checkCashTime(): Boolean {
        val storage = manager.getBodyRequest()?.storage_id
        bd.dateCash()?.searchLastCash(DateGoodsAndCellsEntity.GOODS_ID)?.let {
            if (it.isEmpty()) return false
            val lastItem = it.last()
            val saveTime = Date(lastItem.date)
            val diff: Long = Date().time - saveTime.time
            val seconds = diff / 1000
            val minutes = seconds / 60
            if (storage == lastItem.context && minutes > 60) {
                return false
            }
        }
        return true
    }
}