package com.korett.funnycat.domain.repository

import com.korett.funnycat.domain.model.RemoteCat
import com.korett.funnycat.domain.model.ResultModel
import com.korett.funnycat.domain.model.SavedCat
import kotlinx.coroutines.flow.Flow
import java.io.File

interface CatRepository {

    fun getRemoteCats(number: Int): Flow<ResultModel<List<RemoteCat>>>

    fun getSavedCats(): Flow<ResultModel<List<SavedCat>>>

    suspend fun saveRemoteCat(remoteCat: RemoteCat, date: Long, isLocal: Boolean)

    suspend fun getImageFile(): File

    suspend fun deleteAllTemporaryImages(): Unit?
    suspend fun saveImage(tmpImage: File)
}