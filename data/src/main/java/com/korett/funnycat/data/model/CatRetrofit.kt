package com.korett.funnycat.data.model

import com.korett.funnycat.domain.model.RemoteCat

data class CatRetrofit(
    val id: String,
    val url: String
) {
    fun mapToDomain(): RemoteCat {
        return RemoteCat(
            id = this.id,
            imagePath = this.url
        )
    }
}
