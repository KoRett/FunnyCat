package com.korett.funnycat.presentation.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.korett.funnycat.domain.model.Cat
import com.korett.funnycat.domain.usecase.GetCatsLocalUseCase
import com.korett.funnycat.model.LiveResult
import com.korett.funnycat.model.MutableLiveResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class GalleryViewModel(private val getCatsLocalUseCase: GetCatsLocalUseCase) : ViewModel() {

    private val catsMutableResult: MutableLiveResult<List<Cat>> = MutableLiveResult()
    val catsResult: LiveResult<List<Cat>> = catsMutableResult

    private var getCatsJob: Job? = null

    init {
        getCat()
    }

    private fun getCat() {
        getCatsJob?.cancel()
        getCatsJob = viewModelScope.launch(Dispatchers.IO) {
            getCatsLocalUseCase().collect { result ->
                catsMutableResult.postValue(result)
            }
        }
    }

    class Factory @Inject constructor(
        private val getCatsLocalUseCase: GetCatsLocalUseCase
    ) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GalleryViewModel(getCatsLocalUseCase) as T
        }
    }

}