package com.example.lab_week_11_b

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import org.apache.commons.io.IOUtils
import java.io.File
import java.util.concurrent.Executor

class ProviderFileManager(
    private val context: Context,
    private val fileHelper: FileHelper,
    private val contentResolver: ContentResolver,
    private val executor: Executor,
    private val mediaContentHelper: MediaContentHelper
) {

    fun generatePhotoUri(time: Long): FileInfo {
        val name = "IMG_$time.jpg"
        val file = File(context.getExternalFilesDir(fileHelper.getPicturesFolder()), name)
        return FileInfo(fileHelper.getUriFromFile(file), file, name, fileHelper.getPicturesFolder(), "image/jpeg")
    }

    fun generateVideoUri(time: Long): FileInfo {
        val name = "VID_$time.mp4"
        val file = File(context.getExternalFilesDir(fileHelper.getVideosFolder()), name)
        return FileInfo(fileHelper.getUriFromFile(file), file, name, fileHelper.getVideosFolder(), "video/mp4")
    }

    fun insertImageToStore(fileInfo: FileInfo?) {
        insert(fileInfo, mediaContentHelper.getImageContentUri()) {
            mediaContentHelper.generateImageContentValues(fileInfo!!)
        }
    }

    fun insertVideoToStore(fileInfo: FileInfo?) {
        insert(fileInfo, mediaContentHelper.getVideoContentUri()) {
            mediaContentHelper.generateVideoContentValues(fileInfo!!)
        }
    }

    private fun insert(fileInfo: FileInfo?, storeUri: Uri, values: () -> ContentValues) {
        if (fileInfo == null) return

        executor.execute {
            val uri = contentResolver.insert(storeUri, values()) ?: return@execute
            val input = contentResolver.openInputStream(fileInfo.uri)
            val output = contentResolver.openOutputStream(uri)
            input?.use { inp -> output?.use { out -> IOUtils.copy(inp, out) } }
        }
    }
}
