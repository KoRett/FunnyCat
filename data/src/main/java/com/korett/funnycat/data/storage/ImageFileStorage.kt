package com.korett.funnycat.data.storage

import android.content.Context
import java.io.File
import java.util.UUID

class ImageFileStorage internal constructor(
    private val rootImagesDirectory: File,
    private val temporaryImagesDirectory: File
) : ImageStorage {

    companion object {
        private const val PHOTO_EXTENSION = ".jpg"


        fun create(context: Context): ImageFileStorage {
            val rootDir = File(context.dataDir, "catImages")
            val tmpDir = File(context.dataDir, "temporaryImages")

            if (!rootDir.exists())
                rootDir.mkdir()

            if (!tmpDir.exists())
                tmpDir.mkdir()

            return ImageFileStorage(rootDir, tmpDir)
        }
    }

    override suspend fun moveImageFileToRoot(tmpImage: File) {
        val rootImage = File(rootImagesDirectory, tmpImage.name)
        if (!tmpImage.renameTo(rootImage))
            throw Exception()
    }

    override suspend fun deleteAllTemporaryImages() =
        temporaryImagesDirectory.listFiles()?.forEach { it.delete() }

    override suspend fun getTemporaryImageOutputFile(): File =
        File(temporaryImagesDirectory, generateFilename())

    override suspend fun getAllImageFiles(): List<File>? =
        rootImagesDirectory.listFiles()?.sortedByDescending { it.lastModified() }

    private fun generateFilename(): String = UUID.randomUUID().toString() + PHOTO_EXTENSION
}