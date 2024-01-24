package com.example.musicapp.utils.extensions

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink


fun Uri.getFileSize(context: Context?): Long {
    if (context == null) return -1L
    val contentResolver = context.contentResolver
        // Chúng ta sẽ ưu tiên lấy kích thước tổng của file.
    val length = contentResolver.openFileDescriptor(this, "r")?.use { it.statSize } ?: -1L

    if (length != -1L) {
        return length
    } else {
        // Trường hợp lấy kích thước tổng có vấn đề thì chúng ta mới sử dụng cách này.
        // Tuy nhiên với những file lớn thì sẽ có sai số về kích thước nhận được.
        // Các bạn có thể tham khảo thêm tại:
        // https://stackoverflow.com/questions/48302972/content-resolver-returns-wrong-size
        if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
            return contentResolver.query(this, arrayOf(OpenableColumns.SIZE), null, null, null)
                ?.use { cursor ->
                    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                    if (sizeIndex == -1) {
                        return@use -1L
                    }
                    cursor.moveToFirst()
                    return try {
                        cursor.getLong(sizeIndex)
                    } catch (throwable: Throwable) {
                        -1L
                    }
                } ?: -1L
        } else {
            return -1L
        }
    }
}


