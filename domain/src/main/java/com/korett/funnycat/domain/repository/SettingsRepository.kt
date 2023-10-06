package com.korett.funnycat.domain.repository

import com.korett.funnycat.domain.model.Theme

interface SettingsRepository {

    suspend fun saveTheme(theme: Theme)

    suspend fun getTheme(): Theme

}