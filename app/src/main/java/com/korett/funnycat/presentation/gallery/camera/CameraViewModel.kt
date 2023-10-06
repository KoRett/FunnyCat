package com.korett.funnycat.presentation.gallery.camera

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.korett.funnycat.domain.model.ErrorResultModel
import com.korett.funnycat.domain.model.NothingResultModel
import com.korett.funnycat.domain.model.PendingResultModel
import com.korett.funnycat.domain.model.SuccessResultModel
import com.korett.funnycat.domain.usecase.CreateImageFileUseCase
import com.korett.funnycat.model.LiveResult
import com.korett.funnycat.model.MutableLiveResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class CameraViewModel(private val createImageFileUseCase: CreateImageFileUseCase) : ViewModel() {

    private val imageMutableResult: MutableLiveResult<File> =
        MutableLiveResult(NothingResultModel())
    val imageResult: LiveResult<File> = imageMutableResult

    fun capturePhoto(imageCapture: ImageCapture) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                imageMutableResult.value = PendingResultModel()
            }

            val photoFile = createImageFileUseCase()
            val outputFileOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

            imageCapture.takePicture(
                outputFileOptions,
                Dispatchers.Default.asExecutor(),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        viewModelScope.launch(Dispatchers.IO) {
                            withContext(Dispatchers.Main) {
                                imageMutableResult.value = SuccessResultModel(photoFile)
                            }
                        }
                    }

                    override fun onError(exception: ImageCaptureException) {
                        viewModelScope.launch(Dispatchers.Main) {
                            imageMutableResult.value = ErrorResultModel(exception)
                        }
                    }
                })
        }
    }

    class Factory @Inject constructor(private val createImageFileUseCase: CreateImageFileUseCase) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CameraViewModel(createImageFileUseCase) as T
        }
    }
}