package com.korett.funnycat.domain.usecase

import com.korett.funnycat.domain.model.ResultModel
import com.korett.funnycat.domain.model.SavedCat
import com.korett.funnycat.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedCatsUseCase @Inject constructor(private val catRepository: CatRepository) {

    operator fun invoke(): Flow<ResultModel<List<SavedCat>>> = catRepository.getSavedCats()

}