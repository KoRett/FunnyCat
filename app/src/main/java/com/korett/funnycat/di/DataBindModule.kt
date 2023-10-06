package com.korett.funnycat.di

import com.korett.funnycat.data.repository.CatRepositoryImpl
import com.korett.funnycat.data.repository.SettingsRepositoryImpl
import com.korett.funnycat.data.storage.ImageFileStorage
import com.korett.funnycat.data.storage.ImageStorage
import com.korett.funnycat.data.storage.LocalStorage
import com.korett.funnycat.data.storage.SharedPreferences
import com.korett.funnycat.domain.repository.CatRepository
import com.korett.funnycat.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module

@Module
interface DataBindModule {

    @Binds
    fun bindCatRepositoryImpl_to_CatRepository(catRepositoryImpl: CatRepositoryImpl): CatRepository

    @Binds
    fun bindSettingsRepositoryImpl_to_SettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    fun bindSharedPreferences_to_LocalStorage(sharedPreferences: SharedPreferences): LocalStorage

    @Binds
    fun bindImageCaptureStorage_to_ImageStorage(imageFileStorage: ImageFileStorage): ImageStorage

}