package com.aldredo.core.base.room

import androidx.room.*
import com.aldredo.core.base.room.entity.NomenclatureEntity


@Dao
interface NomenclatureDao {
    @Query("SELECT * FROM NomenclatureEntity")
    fun getAll(): List<NomenclatureEntity?>?

    @Query("SELECT * FROM NomenclatureEntity WHERE id LIKE :idNomenclature")
    fun getNomenclatureToId(idNomenclature: String): NomenclatureEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(nomenclatureEntity: List<NomenclatureEntity>?)
}