package com.korett.funnycat.presentation.cat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.korett.funnycat.domain.model.Cat
import com.korett.funnycat.domain.model.SuccessResultModel
import com.korett.funnycat.domain.model.takeSuccess
import com.korett.funnycat.domain.usecase.SaveCatLocalUseCase
import com.korett.funnycat.domain.usecase.GetCatsInternetUseCase
import com.korett.funnycat.model.LiveResult
import com.korett.funnycat.model.MutableLiveResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatViewModel(
    private val getCatsInternetUseCase: GetCatsInternetUseCase,
    private val saveCatLocalUseCase: SaveCatLocalUseCase
) : ViewModel() {

    private val catsMutableResult: MutableLiveResult<List<Cat>> = MutableLiveResult()
    val catsResult: LiveResult<List<Cat>> = catsMutableResult

    private var getCatJob: Job? = null
    private var saveCatJob: Job? = null

    init {
        getCat()
    }

    fun getCat() {
        getCatJob?.cancel()
        getCatJob = viewModelScope.launch(Dispatchers.IO) {
            getCatsInternetUseCase(number = 1).collect { result ->
                catsMutableResult.postValue(result)
            }
        }
    }

    fun saveCat(date: Long) {
        saveCatJob?.cancel()
        saveCatJob = viewModelScope.launch(Dispatchers.IO) {
            if (catsResult.value is SuccessResultModel)
                saveCatLocalUseCase(catsResult.value.takeSuccess()!![0], date, isLocal = false)
        }
    }

    class Factory @Inject constructor(
        private val getCatsInternetUseCase: GetCatsInternetUseCase,
        private val saveCatLocalUseCase: SaveCatLocalUseCase
    ) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CatViewModel(getCatsInternetUseCase, saveCatLocalUseCase) as T
        }
    }
}