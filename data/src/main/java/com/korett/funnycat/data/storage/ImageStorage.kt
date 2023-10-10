package com.korett.funnycat.data.storage

import java.io.File

interface ImageStorage {


    suspend fun createImageOutputFile(): File
    suspend fun getAllImageFiles(): List<File>?
}