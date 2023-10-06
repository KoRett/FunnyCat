package com.korett.funnycat.data.repository

import com.korett.funnycat.data.storage.CatService
import com.korett.funnycat.data.storage.ImageStorage
import com.korett.funnycat.data.storage.database.dao.CatDao
import com.korett.funnycat.data.storage.database.entities.CatEntity
import com.korett.funnycat.domain.model.Cat
import com.korett.funnycat.domain.model.ErrorResultModel
import com.korett.funnycat.domain.model.PendingResultModel
import com.korett.funnycat.domain.model.ResultModel
import com.korett.funnycat.domain.model.SuccessResultModel
import com.korett.funnycat.domain.repository.CatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatRepositoryImpl @Inject constructor(
    private val catService: CatService,
    private val catDao: CatDao,
    private val imageStorage: ImageStorage
) : CatRepository {

    override fun getCatsInternet(number: Int) = flow<ResultModel<List<Cat>>> {
        emit(PendingResultModel())

        val response = catService.getCats(number)
        if (response.isSuccessful) {
            val cats = response.body()!!.map { it.mapToDomain() }
            emit(SuccessResultModel(cats))
        } else {
            emit(ErrorResultModel(RuntimeException()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getCatCount() = catDao.getCatCount().toLong()

    override fun getCatsLocal() = flow<ResultModel<List<Cat>>> {
        emit(PendingResultModel())
        try {
            val cats = catDao.getCats().map { it.toDomain() }
            delay(500L)
            emit(SuccessResultModel(cats))
        } catch (e: Exception) {
            emit(ErrorResultModel(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getImageFile() = imageStorage.createImageOutputFile()

    override suspend fun saveCatLocal(cat: Cat, date: Long, isLocal: Boolean) {
        val catEntity =
            CatEntity(id = cat.id, imagePath = cat.imagePath, date = date, isLocal = isLocal)
        catDao.addCat(catEntity)
    }

}