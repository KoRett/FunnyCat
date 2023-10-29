package com.korett.funnycat.domain.usecase

import com.korett.funnycat.domain.repository.CatRepository
import javax.inject.Inject

class DeleteAllTemporaryImagesUseCase @Inject constructor(private val catRepository: CatRepository) {

    suspend operator fun invoke() = catRepository.deleteAllTemporaryImages()

}