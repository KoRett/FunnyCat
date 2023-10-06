package com.korett.funnycat.data.storage

import java.io.File

interface ImageStorage {


    suspend fun createImageOutputFile(): File
}