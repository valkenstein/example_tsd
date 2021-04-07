package com.aldredo.qrcode.data.repository

import com.aldredo.core.base.room.AppDatabase
import com.aldredo.core.base.room.entity.DateGoodsAndCellsEntity
import com.aldredo.core.base.room.entity.NomenclatureEntity
import com.aldredo.core.base.util.IManagerInfoDevice
import com.aldredo.qrcode.data.api.CellsApi
import com.aldredo.qrcode.data.model.stateRequest.ResponseStateCells
import retrofit2.Retrofit
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class CellsRepository @Inject constructor(
    private val retrofit: Retrofit,
    private val bd: AppDatabase,
    private val manager: IManagerInfoDevice
) {
    suspend fun getListCells(): ResponseStateCells {
        return try {
            if (checkCashTime()) {
                ResponseStateCells.Error("данные ячеек актуальны")
            } else {

                val body = manager.getBodyRequest()!!
                val cells = retrofit.create(CellsApi::class.java).getCells(body)
                ResponseStateCells.Result(cells)
            }
        } catch (e: Exception) {
            ResponseStateCells.Error(e.message)
        }
    }

    private fun checkCashTime(): Boolean {
        val storage = manager.getBodyRequest()?.storage_id
        bd.dateCash()?.searchLastCash(DateGoodsAndCellsEntity.CELLS_ID)?.let {
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

    suspend fun putGoodsToCell(cells: String, pallet: String): ResponseStateCells {
        return try {
            val response = retrofit.create(CellsApi::class.java).putCellsAndPallet()
            if (response.error)
                ResponseStateCells.Error(response.error_text)
            else
                ResponseStateCells.Result(response)
        } catch (e: Exception) {
            ResponseStateCells.Error(e.message)
        }
    }
}