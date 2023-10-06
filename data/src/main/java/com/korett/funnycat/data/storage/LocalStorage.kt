package com.korett.funnycat.data.storage

import com.korett.funnycat.domain.model.Theme

interface LocalStorage {

    suspend fun saveTheme(theme: Theme)

    suspend fun getTheme(): Theme

}