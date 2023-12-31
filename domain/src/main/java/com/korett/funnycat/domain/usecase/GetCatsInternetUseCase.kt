package com.korett.funnycat.domain.usecase

import com.korett.funnycat.domain.model.RemoteCat
import com.korett.funnycat.domain.model.ResultModel
import com.korett.funnycat.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatsInternetUseCase @Inject constructor(private val catRepository: CatRepository) {

    operator fun invoke(number: Int) : Flow<ResultModel<List<RemoteCat>>> = catRepository.getRemoteCats(number)

}