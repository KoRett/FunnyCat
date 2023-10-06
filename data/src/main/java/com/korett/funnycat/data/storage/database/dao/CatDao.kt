package com.korett.funnycat.data.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.korett.funnycat.data.storage.database.entities.CatEntity

@Dao
interface CatDao {

    @Insert(entity = CatEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCat(catEntity: CatEntity)

    @Query("SELECT * FROM ${CatEntity.TABLE_NAME} ORDER BY date DESC")
    suspend fun getCats(): List<CatEntity>

    @Query("SELECT COUNT(*) FROM ${CatEntity.TABLE_NAME}")
    suspend fun getCatCount(): Long

}