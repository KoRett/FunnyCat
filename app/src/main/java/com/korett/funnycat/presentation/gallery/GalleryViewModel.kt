package com.korett.funnycat.presentation.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.korett.funnycat.domain.model.SavedCat
import com.korett.funnycat.domain.usecase.GetSavedCatsUseCase
import com.korett.funnycat.model.LiveResult
import com.korett.funnycat.model.MutableLiveResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class GalleryViewModel(private val getSavedCatsUseCase: GetSavedCatsUseCase) : ViewModel() {

    private val catsMutableResult: MutableLiveResult<List<SavedCat>> = MutableLiveResult()
    val catsResult: LiveResult<List<SavedCat>> = catsMutableResult

    private var getCatsJob: Job? = null

    fun getCat() {
        getCatsJob?.cancel()
        getCatsJob = viewModelScope.launch(Dispatchers.IO) {
            getSavedCatsUseCase().collect { result ->
                catsMutableResult.postValue(result)
            }
        }
    }

    class Factory @Inject constructor(
        private val getSavedCatsUseCase: GetSavedCatsUseCase
    ) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GalleryViewModel(getSavedCatsUseCase) as T
        }
    }

}