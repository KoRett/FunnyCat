package com.korett.funnycat.presentation.cat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.korett.funnycat.domain.model.RemoteCat
import com.korett.funnycat.domain.model.SuccessResultModel
import com.korett.funnycat.domain.model.takeSuccess
import com.korett.funnycat.domain.usecase.GetCatsInternetUseCase
import com.korett.funnycat.domain.usecase.SaveRemoteCatUseCase
import com.korett.funnycat.model.LiveResult
import com.korett.funnycat.model.MutableLiveResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatViewModel(
    private val getCatsInternetUseCase: GetCatsInternetUseCase,
    private val saveRemoteCatUseCase: SaveRemoteCatUseCase
) : ViewModel() {

    private val catsMutableResult: MutableLiveResult<List<RemoteCat>> = MutableLiveResult()
    val catsResult: LiveResult<List<RemoteCat>> = catsMutableResult

    private var getCatJob: Job? = null
    private var saveCatJob: Job? = null

    init {
        getCat()
    }

    fun getCat() {
        if (getCatJob?.isActive != true) {
            getCatJob = viewModelScope.launch(Dispatchers.IO) {
                getCatsInternetUseCase(number = 1).collect { result ->
                    catsMutableResult.postValue(result)
                }
            }
        }
    }

    fun saveCat(date: Long) {
        saveCatJob?.cancel()
        saveCatJob = viewModelScope.launch(Dispatchers.IO) {
            if (catsResult.value is SuccessResultModel)
                saveRemoteCatUseCase(catsResult.value.takeSuccess()!![0], date, isLocal = false)
        }
    }

    class Factory @Inject constructor(
        private val getCatsInternetUseCase: GetCatsInternetUseCase,
        private val saveRemoteCatUseCase: SaveRemoteCatUseCase
    ) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CatViewModel(getCatsInternetUseCase, saveRemoteCatUseCase) as T
        }
    }
}