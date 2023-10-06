package com.korett.funnycat.domain.repository

import com.korett.funnycat.domain.model.Cat
import com.korett.funnycat.domain.model.ResultModel
import kotlinx.coroutines.flow.Flow
import java.io.File

interface CatRepository {

    fun getCatsInternet(number: Int): Flow<ResultModel<List<Cat>>>

    fun getCatsLocal(): Flow<ResultModel<List<Cat>>>

    suspend fun getCatCount(): Long

    suspend fun saveCatLocal(cat: Cat, date: Long, isLocal: Boolean)
    suspend fun getImageFile(): File
}