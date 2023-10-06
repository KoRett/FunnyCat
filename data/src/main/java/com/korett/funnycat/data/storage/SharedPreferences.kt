package com.korett.funnycat.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.korett.funnycat.domain.model.Theme
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferences @Inject constructor(context: Context) : LocalStorage {

    private val pref: SharedPreferences =
        context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    private val editor = pref.edit()

    override suspend fun saveTheme(theme: Theme) {
        editor.putInt(APP_THEME, theme.value).commit()
    }

    override suspend fun getTheme(): Theme = (Theme from pref.getInt(APP_THEME, Theme.System.value))!!

    companion object {
        const val APP_PREFERENCES = "local_storage"
        const val APP_THEME = "theme"
    }
}