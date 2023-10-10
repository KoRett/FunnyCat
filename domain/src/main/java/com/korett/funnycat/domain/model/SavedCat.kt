package com.korett.funnycat.domain.model

data class SavedCat(
    val id: String,
    val imagePath: String,
    val isLocal: Boolean,
    val date: Long
)