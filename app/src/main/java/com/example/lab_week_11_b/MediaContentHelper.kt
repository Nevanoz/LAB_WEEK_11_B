package com.example.lab_week_11_b

import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

class MediaContentHelper {

    fun getImageContentUri(): Uri =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        else
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    fun getVideoContentUri(): Uri =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        else
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI

    fun generateImageContentValues(fileInfo: FileInfo) = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, fileInfo.name)
        put(MediaStore.Images.Media.MIME_TYPE, fileInfo.mimeType)
        put(MediaStore.Images.Media.RELATIVE_PATH, fileInfo.relativePath)
    }

    fun generateVideoContentValues(fileInfo: FileInfo) = ContentValues().apply {
        put(MediaStore.Video.Media.DISPLAY_NAME, fileInfo.name)
        put(MediaStore.Video.Media.MIME_TYPE, fileInfo.mimeType)
        put(MediaStore.Video.Media.RELATIVE_PATH, fileInfo.relativePath)
    }
}
