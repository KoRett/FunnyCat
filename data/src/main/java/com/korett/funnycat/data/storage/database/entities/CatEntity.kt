package com.korett.funnycat.data.storage.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.korett.funnycat.data.storage.database.entities.CatEntity.Companion.TABLE_NAME
import com.korett.funnycat.domain.model.Cat

@Entity(tableName = TABLE_NAME)
data class CatEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "image_path") val imagePath: String,
    val date: Long,
    @ColumnInfo(name = "is_local") val isLocal: Boolean
) {

    fun toDomain(): Cat {
        return Cat(id = this.id, imagePath = this.imagePath, isLocal = this.isLocal)
    }

    companion object {
        const val TABLE_NAME = "cat_table"
    }
}