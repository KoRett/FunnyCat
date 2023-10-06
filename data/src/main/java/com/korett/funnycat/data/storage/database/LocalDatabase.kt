package com.korett.funnycat.data.storage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.korett.funnycat.data.storage.database.dao.*
import com.korett.funnycat.data.storage.database.entities.*

@Database(
    entities = [
        CatEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun createCatDao(): CatDao

    companion object {
        fun create(context: Context): LocalDatabase {
            return Room.databaseBuilder(
                context = context,
                LocalDatabase::class.java,
                name = "local_database.db"
            ).build()
        }
    }
}