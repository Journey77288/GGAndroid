package io.ganguo.picker.core.entity

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import io.ganguo.picker.MimeType
import kotlinx.parcelize.Parcelize

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/06/22
 *     desc   : 封装Bucket内查询结果的实体类
 * </pre>
 */
/**
 * 封装Bucket内查询结果的实体类
 * @param
 * @see
 * @author Raynor
 * @property [id] 媒体资源的[MediaStore.Files.FileColumns._ID]
 * @property [mimeType]
 * @property [uri]
 * @property [size] 文件大小 单位应该是字节
 * @property [duration] 视频的时间长度 单位是ms
 */
@Parcelize
data class Media constructor(
        var id: Long = 0,
        var mimeType: String? = null,
        var uri: Uri? = null,
        var size: Long = 0,
        var duration: Long = 0
) : Parcelable {


    constructor(cursor: Cursor?) : this() {
        check(cursor != null) { "cursor must not to be null" }
        id = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID))
        mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE))
        size = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE))
        duration = cursor.getLong(cursor.getColumnIndex("duration"))

        val contentUri: Uri = if (isImage()) {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        } else if (isVideo()) {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        } else {
            MediaStore.Files.getContentUri("external")
        }

        uri = ContentUris.withAppendedId(contentUri, id)
    }

    fun isCapture(): Boolean {
        return id == ID_CAPTURE
    }

    fun isImage(): Boolean {
        return MimeType.isImage(mimeType)
    }

    fun isGif(): Boolean {
        return MimeType.isGif(mimeType)
    }

    fun isVideo(): Boolean {
        return MimeType.isVideo(mimeType)
    }

    fun isAudio(): Boolean {
        return MimeType.isAudio(mimeType)
    }

    companion object {
        const val ID_CAPTURE: Long = -1
        const val ITEM_DISPLAY_NAME_CAPTURE = "Capture"
    }
}