package com.korett.funnycat.domain.usecase

import com.korett.funnycat.domain.model.RemoteCat
import com.korett.funnycat.domain.repository.CatRepository
import javax.inject.Inject

class SaveCatUseCase @Inject constructor(private val catRepository: CatRepository) {

    suspend operator fun invoke(remoteCat: RemoteCat, date: Long, isLocal: Boolean) =
        catRepository.saveCat(remoteCat, date, isLocal)

}