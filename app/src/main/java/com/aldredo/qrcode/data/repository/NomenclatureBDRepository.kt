package com.aldredo.qrcode.data.repository

import com.aldredo.core.base.room.AppDatabase
import com.aldredo.core.base.room.entity.DateGoodsAndCellsEntity
import com.aldredo.core.base.room.entity.NomenclatureEntity
import com.aldredo.core.base.room.entity.SerialEntity
import com.aldredo.core.base.util.IManagerInfoDevice
import com.aldredo.qrcode.data.model.NomenclatureModel
import com.aldredo.qrcode.data.model.SeriesAndGoods
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class NomenclatureBDRepository @Inject constructor(
    private val bd: AppDatabase,
    private val manager: IManagerInfoDevice
) {
    fun saveNomenclatureToBD(result: ArrayList<NomenclatureModel>): String {
        return try {
            val mapping = mappingNomenclatureModelToBdEntity(result)
            bd.nomenclatureDao()?.insert(mapping.nomenclatures)
            bd.serialDao()?.insert(mapping.series)
            bd.dateCash()
                ?.insert(
                    DateGoodsAndCellsEntity(
                        DateGoodsAndCellsEntity.GOODS_ID,
                        manager.getBodyRequest()?.storage_id ?: "-",
                        Date().time
                    )
                )

            "кэширование номенклатуры прошло успешно"
        } catch (e: Exception) {
            e.message.toString() + "произошла ошибка"
        }
    }

    fun searchSerialToBd(serial: String): SerialEntity? {
        return try {
            bd.serialDao()?.searchSerial(serial)
        } catch (e: Exception) {
            val p = e.message
            null
        }
    }

    private fun mappingNomenclatureModelToBdEntity(nomenclatures: ArrayList<NomenclatureModel>): SeriesAndGoods {
        val nomenctlatures: ArrayList<NomenclatureEntity> = arrayListOf()
        val series: ArrayList<SerialEntity> = arrayListOf()
        nomenclatures.forEach { nomenclatureItem ->
            nomenctlatures.add(
                NomenclatureEntity(
                    id = nomenclatureItem.id ?: "",
                    name = nomenclatureItem.name ?: "",
                    id_nomenclature = nomenclatureItem.id ?: "",
                    articul = nomenclatureItem.articul ?: "",
                ).apply {
                    nomenclatureItem.serial?.forEach {
                        series.add(SerialEntity(id_nomenclature = id, series = it))
                    }
                })
        }
        return SeriesAndGoods(nomenctlatures, series)
    }

    fun getNameForSeries(id: String): String? {
        return bd.nomenclatureDao()?.getNomenclatureToId(id)?.name
    }
}