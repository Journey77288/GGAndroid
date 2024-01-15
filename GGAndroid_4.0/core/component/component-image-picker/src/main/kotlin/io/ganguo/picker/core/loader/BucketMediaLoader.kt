package io.ganguo.picker.core.loader

import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.database.MergeCursor
import android.net.Uri
import android.provider.MediaStore
import androidx.loader.content.CursorLoader
import io.ganguo.picker.core.bucket.Bucket
import io.ganguo.picker.core.entity.Media
import io.ganguo.picker.core.entity.PickerSpec
import io.ganguo.picker.util.MediaUtils

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/06/08
 *     desc   : 查询MediaStorage中某一特定相册(Bucket)中所有资源文件 所使用的CursorLoader
 * </pre>
 *
 * 查询MediaStorage中某一特定相册(Bucket)中所有资源文件 所使用的CursorLoader
 * @param
 * @see
 * @author Raynor
 * @property [QUERY_URI] 查询URI
 * @property [BUCKET_MEDIA_ORDER_BY] 排序字段
 * @property [PROJECTION] 表示想要从MediaStorage中查询的列
 */
class BucketMediaLoader private constructor(context: Context,
                                            uri: Uri = QUERY_URI,
                                            projection: Array<String> = PROJECTION,
                                            selection: String,
                                            selectionArgs: Array<String>,
                                            orderBy: String = BUCKET_MEDIA_ORDER_BY,
                                            private val enableCapture: Boolean) : CursorLoader(context, uri, projection, selection, selectionArgs, orderBy) {

    companion object {
        private val QUERY_URI = MediaStore.Files.getContentUri("external")
        private const val BUCKET_MEDIA_ORDER_BY = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"

        /**
         * 查询字段
         */
        private val PROJECTION = arrayOf(
                MediaStore.Files.FileColumns._ID,
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.MIME_TYPE,
                MediaStore.MediaColumns.SIZE,
                "duration")

        /**
         * 查询"全部"媒体文件 + 不限制媒体格式（图片+视频 没有GIF）
         */
        private const val SELECTION_ALL = "(${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE}=?) AND ${MediaStore.MediaColumns.SIZE}>0 AND ${MediaStore.MediaColumns.MIME_TYPE}!=?"
        private val SELECTION_ALL_ARGS = arrayOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(), MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString(), "image/gif")

        /**
         * 查询"全部"媒体文件 + 限定一种媒体格式（图片/视频/音频）
         */
        private const val SELECTION_ALL_FOR_SINGLE_MEDIA_TYPE = "${MediaStore.Files.FileColumns.MEDIA_TYPE}=? AND ${MediaStore.MediaColumns.SIZE}>0 AND ${MediaStore.MediaColumns.MIME_TYPE}!=?"

        private fun getSelectionArgsForSingleMediaType(mediaType: Int) = arrayOf(mediaType.toString(), "image/gif")


        /**
         * 查询"指定相册"的媒体文件 + 不限制媒体格式（图片+视频 没有GIF）
         */
        private const val SELECTION_ALBUM = "(${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE}=?) AND  bucket_id=? AND ${MediaStore.MediaColumns.SIZE}>0 AND ${MediaStore.MediaColumns.MIME_TYPE}!=?"

        private fun getSelectionSpecifiedAlbumArgsForAllType(bucketId: String) = arrayOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(), MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString(), bucketId, "image/gif")

        /**
         * 查询"指定相册"的媒体文件 + 限定一种媒体格式（图片/视频/GIF）
         */
        private const val SELECTION_ALBUM_FOR_SINGLE_MEDIA_TYPE = (MediaStore.Files.FileColumns.MEDIA_TYPE + "=?" + " AND " + " bucket_id=?" + " AND " + MediaStore.MediaColumns.SIZE + ">0" + " AND ${MediaStore.MediaColumns.MIME_TYPE}!=?")

        private fun getSelectionSpecifiedAlbumArgsForSingleMediaType(mediaType: Int, bucketId: String) = arrayOf(mediaType.toString(), bucketId, "image/gif")


        /**
         * 查询"全部"媒体文件 + 限定一种媒体格式（图片/视频） + GIF
         */
        private const val SELECTION_ALL_FOR_GIF = "${MediaStore.Files.FileColumns.MEDIA_TYPE}=? AND ${MediaStore.MediaColumns.MIME_TYPE}=? AND ${MediaStore.MediaColumns.SIZE}>0"

        private fun getSelectionArgsForGifType(mediaType: Int) = arrayOf(mediaType.toString(), "image/gif")


        /**
         * 查询"指定相册"的媒体文件 + 限定一种媒体格式（图片/视频） + GIF
         */
        private val SELECTION_ALBUM_FOR_GIF = "${MediaStore.Files.FileColumns.MEDIA_TYPE}=? AND  bucket_id=? AND ${MediaStore.MediaColumns.MIME_TYPE}=? AND ${MediaStore.MediaColumns.SIZE}>0"

        private fun getSelectionSpecifiedAlbumArgsForGifType(mediaType: Int, bucketId: String) = arrayOf(mediaType.toString(), bucketId, "image/gif")

        /**
         * 创建新的BucketMediaLoader实例
         */
        fun newInstance(context: Context, bucket: Bucket, enableCapture: Boolean): BucketMediaLoader {
            var selection = ""
            var selectionArgs = arrayOf("")

            if (bucket.isAll()) {
                when {
                    PickerSpec.isOnlyShowGif() -> {
                        selection = SELECTION_ALL_FOR_GIF
                        selectionArgs = getSelectionArgsForGifType(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)
                    }
                    PickerSpec.isOnlyShowImages() -> {
                        selection = SELECTION_ALL_FOR_SINGLE_MEDIA_TYPE
                        selectionArgs = getSelectionArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)
                    }
                    PickerSpec.isOnlyShowVideos() -> {
                        selection = SELECTION_ALL_FOR_SINGLE_MEDIA_TYPE
                        selectionArgs = getSelectionArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO)
                    }
                    PickerSpec.isOnlyShowAudios() -> {
                        selection = SELECTION_ALL_FOR_SINGLE_MEDIA_TYPE
                        selectionArgs = getSelectionArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO)
                    }
                    else -> {
                        selection = SELECTION_ALL
                        selectionArgs = SELECTION_ALL_ARGS
                    }
                }
            } else {
                when {
                    PickerSpec.isOnlyShowGif() -> {
                        selection = SELECTION_ALBUM_FOR_GIF
                        selectionArgs = getSelectionSpecifiedAlbumArgsForGifType(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE, bucket.id)
                    }
                    PickerSpec.isOnlyShowImages() -> {
                        selection = SELECTION_ALBUM_FOR_SINGLE_MEDIA_TYPE
                        selectionArgs = getSelectionSpecifiedAlbumArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE, bucket.id)
                    }
                    PickerSpec.isOnlyShowVideos() -> {
                        selection = SELECTION_ALBUM_FOR_SINGLE_MEDIA_TYPE
                        selectionArgs = getSelectionSpecifiedAlbumArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO, bucket.id)
                    }
                    PickerSpec.isOnlyShowAudios() -> {
                        selection = SELECTION_ALBUM_FOR_SINGLE_MEDIA_TYPE
                        selectionArgs = getSelectionSpecifiedAlbumArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO, bucket.id)
                    }
                    else -> {
                        selection = SELECTION_ALBUM
                        selectionArgs = getSelectionSpecifiedAlbumArgsForAllType(bucket.id)
                    }
                }
            }

            return BucketMediaLoader(context = context, selection = selection, selectionArgs = selectionArgs, enableCapture = enableCapture)
        }
    }

    override fun loadInBackground(): Cursor? {
        val result = super.loadInBackground()
        if (!enableCapture || !MediaUtils.hasCamera(context)) return result
        val dump = MatrixCursor(PROJECTION)
        dump.addRow(arrayOf(Media.ID_CAPTURE, Media.ITEM_DISPLAY_NAME_CAPTURE, "", 0, 0))
        return MergeCursor(arrayOf(dump, result))
    }
}