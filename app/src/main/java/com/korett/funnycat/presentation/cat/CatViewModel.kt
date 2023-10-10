package com.korett.funnycat.presentation.cat

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.korett.funnycat.domain.model.RemoteCat
import com.korett.funnycat.domain.model.SuccessResultModel
import com.korett.funnycat.domain.model.takeSuccess
import com.korett.funnycat.domain.usecase.CreateImageFileUseCase
import com.korett.funnycat.domain.usecase.GetCatsInternetUseCase
import com.korett.funnycat.domain.usecase.SaveCatUseCase
import com.korett.funnycat.model.LiveResult
import com.korett.funnycat.model.MutableLiveResult
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

//TODO Remove download image feature
class CatViewModel(
    private val getCatsInternetUseCase: GetCatsInternetUseCase,
    private val saveCatUseCase: SaveCatUseCase,
    private val createImageFileUseCase: CreateImageFileUseCase
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
                saveCatUseCase(catsResult.value.takeSuccess()!![0], date, isLocal = false)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    fun downloadImageFromPath(path: String?) {
        val network = newSingleThreadContext("Network")
        val disk = newSingleThreadContext("Disk")
        viewModelScope.launch(network) {
            try {
                val url = URL(path)
                val con = url.openConnection() as HttpURLConnection
                con.doInput = true
                con.connect()
                if (con.responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = con.inputStream
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream.close()
                    withContext(disk) {
                        val fos: OutputStream = FileOutputStream(createImageFileUseCase())
                        fos.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }
                    }
                }
            } catch (ex: Exception) {
                Log.e("Exception", ex.toString())
            }
        }
    }

    class Factory @Inject constructor(
        private val getCatsInternetUseCase: GetCatsInternetUseCase,
        private val saveCatUseCase: SaveCatUseCase,
        private val createImageFileUseCase: CreateImageFileUseCase
    ) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CatViewModel(getCatsInternetUseCase, saveCatUseCase, createImageFileUseCase) as T
        }
    }
}