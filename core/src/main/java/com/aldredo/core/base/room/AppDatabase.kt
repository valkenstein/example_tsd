package com.aldredo.core.base.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aldredo.core.base.room.entity.CellsEntity
import com.aldredo.core.base.room.entity.DateGoodsAndCellsEntity
import com.aldredo.core.base.room.entity.NomenclatureEntity
import com.aldredo.core.base.room.entity.SerialEntity


@Database(
    entities = [NomenclatureEntity::class, CellsEntity::class, SerialEntity::class, DateGoodsAndCellsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun nomenclatureDao(): NomenclatureDao?

    abstract fun serialDao(): SerialDao?

    abstract fun cellsDao(): CellsDao?

    abstract fun dateCash(): DateGoodsCash?
}