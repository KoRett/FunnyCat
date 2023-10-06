package com.korett.funnycat.di

import android.content.Context
import com.korett.funnycat.data.di.RoomModel
import com.korett.funnycat.data.storage.CatService
import com.korett.funnycat.data.storage.ImageFileStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RoomModel::class])
class DataModule {

    @Provides
    @Singleton
    fun provideRetrofitCatAPI(): CatService = CatService.create()

    @Provides
    @Singleton
    fun provideImageCaptureStorage(context: Context): ImageFileStorage =
        ImageFileStorage.create(context)

}