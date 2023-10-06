package com.korett.funnycat.domain.usecase

import com.korett.funnycat.domain.model.Cat
import com.korett.funnycat.domain.repository.CatRepository
import javax.inject.Inject

class SaveCatLocalUseCase @Inject constructor(private val catRepository: CatRepository) {

    suspend operator fun invoke(cat: Cat, date: Long, isLocal: Boolean) =
        catRepository.saveCatLocal(cat, date, isLocal)

}