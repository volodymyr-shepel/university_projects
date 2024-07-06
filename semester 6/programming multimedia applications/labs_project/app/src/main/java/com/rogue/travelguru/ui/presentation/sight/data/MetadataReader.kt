package com.rogue.travelguru.ui.presentation.sight.data

import android.app.Application
import android.net.Uri
import android.provider.MediaStore

data class Metadata(
    val filename: String
)

interface MetadataReader {
    fun getContent(uri: Uri): Metadata?
}

class MetadataFileReader (
    private val app: Application
): MetadataReader {
    override fun getContent(uri: Uri): Metadata? {
        if (uri.scheme != "content")
            return null

        val filename = app.contentResolver
            .query(
                uri,
                arrayOf(MediaStore.Video.VideoColumns.DISPLAY_NAME),
                null, null, null
            )?.use {
                cursor ->
                val index = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                cursor.getString(index)
            }

        return filename?.let {
            file -> Metadata(
                filename = Uri.parse(file).lastPathSegment ?: return null
            )
        }
    }

}