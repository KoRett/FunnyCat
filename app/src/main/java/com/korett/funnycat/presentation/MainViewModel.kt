package com.korett.funnycat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.korett.funnycat.domain.model.Theme
import com.korett.funnycat.domain.usecase.GetThemeUseCase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject

class MainViewModel(private val getThemeUseCase: GetThemeUseCase) : ViewModel() {

    fun getTheme(): Deferred<Theme> = viewModelScope.async {
        getThemeUseCase()
    }

    class Factory @Inject constructor(private val getThemeUseCase: GetThemeUseCase) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(getThemeUseCase) as T
        }
    }
}