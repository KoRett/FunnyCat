package com.korett.funnycat.domain.usecase

import com.korett.funnycat.domain.repository.CatRepository
import java.io.File
import javax.inject.Inject

class SaveImageUseCase @Inject constructor(private val catRepository: CatRepository) {

    suspend operator fun invoke(tmpImage: File) = catRepository.saveImage(tmpImage)

}