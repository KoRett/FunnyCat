package com.korett.funnycat.domain.usecase

import com.korett.funnycat.domain.model.Cat
import com.korett.funnycat.domain.model.ResultModel
import com.korett.funnycat.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatsLocalUseCase @Inject constructor(private val catRepository: CatRepository) {

    operator fun invoke(): Flow<ResultModel<List<Cat>>> = catRepository.getCatsLocal()

}