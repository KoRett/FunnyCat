package com.korett.funnycat.data.model

import com.korett.funnycat.domain.model.Cat

data class CatRetrofit(
    val id: String,
    val url: String
) {
    fun mapToDomain(): Cat {
        return Cat(
            id = this.id,
            imagePath = this.url,
            isLocal = false
        )
    }
}
