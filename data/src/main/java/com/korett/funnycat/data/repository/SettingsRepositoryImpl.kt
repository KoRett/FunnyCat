package com.korett.funnycat.data.repository

import com.korett.funnycat.data.storage.LocalStorage
import com.korett.funnycat.domain.model.Theme
import com.korett.funnycat.domain.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(private val localStorage: LocalStorage) :
    SettingsRepository {
    override suspend fun saveTheme(theme: Theme) = localStorage.saveTheme(theme = theme)

    override suspend fun getTheme(): Theme = localStorage.getTheme()

}