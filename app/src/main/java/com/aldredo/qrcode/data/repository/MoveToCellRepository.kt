package com.aldredo.qrcode.data.repository

import com.aldredo.core.base.util.IManagerInfoDevice
import com.aldredo.qrcode.data.api.MoveToCellApi
import com.aldredo.qrcode.data.model.SerialModel
import com.aldredo.qrcode.data.model.stateRequest.ResponseStateMoveToCell
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject

class MoveToCellRepository @Inject constructor(
    private val retrofit: Retrofit,
    private val manager: IManagerInfoDevice
) {
    suspend fun putSerialAndCells(
        cells: String,
        series: List<SerialModel>
    ): ResponseStateMoveToCell {
        return try {
            val response = retrofit.create(MoveToCellApi::class.java)
                .saveBarcodeToServer(hashMap)
            ResponseStateMoveToCell.Result(response)

        } catch (e: Exception) {
            ResponseStateMoveToCell.Error(e.message.toString())
        }
    }

    private fun mappingToArray(nomenclatureEntity: List<SerialModel>): ArrayList<String> {
        val list = ArrayList<String>()
        nomenclatureEntity.forEach {
            list.add(it.series)
        }
        return list
    }


}