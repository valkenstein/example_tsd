package com.aldredo.qrcode.data.repository

import com.aldredo.core.base.room.AppDatabase
import com.aldredo.core.base.room.entity.CellsEntity
import com.aldredo.core.base.room.entity.DateGoodsAndCellsEntity
import com.aldredo.core.base.util.IManagerInfoDevice
import com.aldredo.qrcode.data.model.CellsResponse
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class CellsBdRepository @Inject constructor(
    private val bd: AppDatabase,
    private val manager: IManagerInfoDevice
) {
    fun saveCellsToBd(result: ArrayList<CellsResponse.CellsModel>): String {
        return try {
                bd.cellsDao()?.insert(mappingCellsModelToBdEntity(result))
                bd.dateCash()
                    ?.insert(
                        DateGoodsAndCellsEntity(
                            DateGoodsAndCellsEntity.CELLS_ID,
                            manager.getBodyRequest()?.storage_id ?: "-",
                            Date().time
                        )
                    )
                "кэширование ячеек прошло успешно"

        } catch (e: Exception) {
            e.message.toString() + "произошла ошибка"
        }
    }



    fun searchBarcodeToBd(search: String): CellsEntity? = try {
        (bd.cellsDao()?.searchBarcode(search))
    } catch (e: Exception) {
        null
    }

    private fun mappingCellsModelToBdEntity(cells: ArrayList<CellsResponse.CellsModel>) =
        ArrayList<CellsEntity>().apply {
            cells.forEach {
                add(
                    CellsEntity(
                        type = it.type,
                        barcode = it.barcode,
                        count_goods = it.count_goods,
                        count_series = it.count_series,
                        name = it.name
                    )
                )
            }
        }

    fun getCells() = bd.cellsDao()?.getAll()
}
