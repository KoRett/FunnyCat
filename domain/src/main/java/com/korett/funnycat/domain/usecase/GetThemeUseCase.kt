package com.korett.funnycat.domain.usecase

import com.korett.funnycat.domain.model.Theme
import com.korett.funnycat.domain.repository.SettingsRepository
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {

    suspend operator fun invoke(): Theme = settingsRepository.getTheme()

}