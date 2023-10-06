package com.korett.funnycat.data.di

import android.content.Context
import com.korett.funnycat.data.storage.database.LocalDatabase
import com.korett.funnycat.data.storage.database.dao.CatDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModel {

    @Provides
    @Singleton
    fun provideLocalDatabase(context: Context): LocalDatabase {
        return LocalDatabase.create(context)
    }

    @Provides
    @Singleton
    fun provideCatDao(localDatabase: LocalDatabase): CatDao = localDatabase.createCatDao()

}