package com.korett.funnycat.data.storage

import java.io.File

interface ImageStorage {

    suspend fun getTemporaryImageOutputFile(): File

    suspend fun getAllImageFiles(): List<File>?

    suspend fun moveImageFileToRoot(tmpImage: File)

    suspend fun deleteAllTemporaryImages(): Unit?
}