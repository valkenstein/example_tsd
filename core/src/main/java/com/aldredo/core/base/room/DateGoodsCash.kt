package com.aldredo.core.base.room

import androidx.room.*
import com.aldredo.core.base.room.entity.DateGoodsAndCellsEntity

@Dao
interface DateGoodsCash {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dateAndCells: DateGoodsAndCellsEntity)

    @Query("SELECT * FROM DateGoodsAndCellsEntity")
    fun select(): List<DateGoodsAndCellsEntity>

    @Query("SELECT* FROM DateGoodsAndCellsEntity WHERE id=:id")
    fun searchLastCash(id: String): List<DateGoodsAndCellsEntity>
}