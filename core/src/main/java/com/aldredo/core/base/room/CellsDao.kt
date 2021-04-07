package com.aldredo.core.base.room

import androidx.room.*
import com.aldredo.core.base.room.entity.CellsEntity

@Dao
interface CellsDao {
    @Query("SELECT * FROM CellsEntity")
    fun getAll(): List<CellsEntity?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cells: List<CellsEntity>?)

    @Query("SELECT * FROM CellsEntity WHERE barcode LIKE :searchBarcode")
    fun searchBarcode(searchBarcode: String): CellsEntity?

    @Delete
    fun delete(cells: CellsEntity?)
}