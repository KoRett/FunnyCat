package com.korett.funnycat.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.korett.funnycat.domain.usecase.GetCatUseCase
import com.korett.funnycat.model.LiveResult
import com.korett.funnycat.model.MutableLiveResult
import javax.inject.Inject

class CatViewModel(val getCatUseCase: GetCatUseCase) : ViewModel() {

    private val catsLiveResult = MutableLiveResult<Int>()
    val catsLive: LiveResult<Int> = catsLiveResult

    class Factory @Inject constructor(private val getCatUseCase: GetCatUseCase) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CatViewModel(getCatUseCase) as T
        }
    }
}