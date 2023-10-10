package com.korett.funnycat.data.storage

import android.content.Context
import java.io.File
import java.util.UUID

class ImageFileStorage internal constructor(private val rootDirectory: File) : ImageStorage {

    companion object {
        private const val PHOTO_EXTENSION = ".jpg"

        fun create(context: Context): ImageFileStorage {
            val dir = File(context.dataDir, "catImages")

            if (!dir.exists())
                dir.mkdir()

            return ImageFileStorage(dir)
        }
    }

    override suspend fun createImageOutputFile(): File = File(rootDirectory, generateFilename())

    override suspend fun getAllImageFiles(): List<File>? =
        rootDirectory.listFiles()?.sortedByDescending { it.lastModified() }

    private fun generateFilename(): String = UUID.randomUUID().toString() + PHOTO_EXTENSION
}