package com.aldredo.core.base.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aldredo.core.base.room.entity.SerialEntity

@Dao
interface SerialDao {
    @Query("SELECT * FROM SerialEntity")
    fun getSeries(): List<SerialEntity>

    @Query("SELECT * FROM SerialEntity WHERE series LIKE :seriesSearch")
    fun searchSerial(seriesSearch: String): SerialEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(nomenclatureEntity: List<SerialEntity>?)
}