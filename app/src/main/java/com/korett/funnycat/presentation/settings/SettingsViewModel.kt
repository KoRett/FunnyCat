package com.korett.funnycat.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.korett.funnycat.domain.model.Theme
import com.korett.funnycat.domain.usecase.GetThemeUseCase
import com.korett.funnycat.domain.usecase.SaveThemeUseCase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel(
    private val saveThemeUseCase: SaveThemeUseCase,
    private val getThemeUseCase: GetThemeUseCase
) : ViewModel() {

    fun saveTheme(theme: Theme) {
        viewModelScope.launch(Dispatchers.IO) {
            saveThemeUseCase(theme = theme)
        }
    }

    fun getTheme(): Deferred<Theme> = viewModelScope.async {
        getThemeUseCase()
    }

    class Factory @Inject constructor(
        private val saveThemeUseCase: SaveThemeUseCase,
        private val getThemeUseCase: GetThemeUseCase
    ) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SettingsViewModel(saveThemeUseCase, getThemeUseCase) as T
        }
    }
}