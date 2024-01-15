package io.ganguo.picker.core.loader

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.database.MergeCursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.loader.content.CursorLoader
import io.ganguo.picker.MimeType
import io.ganguo.picker.core.bucket.Bucket
import io.ganguo.picker.core.entity.PickerSpec
import io.ganguo.picker.core.loader.BucketLoader.Companion.COLUMNS
import io.ganguo.picker.core.loader.BucketLoader.Companion.PROJECTION
import io.ganguo.picker.core.loader.BucketLoader.Companion.PROJECTION_29

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/06/04
 *     desc   : 查询MediaStorage中的相册(Bucket)Loader
 * </pre>
 *
 * 查询MediaStorage中的相册(Bucket)所使用的CursorLoader
 * CursorLoader 本身是对ContentProvider的一层封装
 * @param
 * @see
 * @author Raynor
 * @property [COLUMNS] 向外返回的结果列定义
 * @property [PROJECTION] [PROJECTION_29] 表示想要从MediaStorage中查询的列
 */
class BucketLoader private constructor(context: Context,
                                       uri: Uri = QUERY_URI,
                                       projection: Array<String>,
                                       selection: String,
                                       selectionArgs: Array<String>,
                                       orderBy: String = BUCKET_ORDER_BY) : CursorLoader(context, uri, projection, selection, selectionArgs, orderBy) {

    /**
     * 子线程查询数据
     * 因为Android10无法直接使用count(*) 查询Bucket内文件数量
     * 所以根据系统版本不同 有两种实现方法
     */
    override fun loadInBackground(): Cursor? {
        val originalBucketCursor = super.loadInBackground()
        return if (beforeAndroid10()) {
            queryBeforeAndroid10(originalBucketCursor)
        } else {
            queryAndroid10AndAbove(originalBucketCursor)
        }
    }

    private fun queryBeforeAndroid10(originalBucketCursor: Cursor?): Cursor? {
        //"所有"分类的文件数量
        var totalCount = 0
        //"所有"分类的封面图片Uri
        var allBucketCoverUri: Uri? = null
        //记录第一条 表示所有媒体文件
        val allBucketCursor = MatrixCursor(COLUMNS)
        //记录2～N条 表示每一个Bucket
        val otherBucketCursor = MatrixCursor(COLUMNS)

        if (originalBucketCursor != null) {
            while (originalBucketCursor.moveToNext()) {
                val fileId: Long = originalBucketCursor.getLong(originalBucketCursor.getColumnIndex(MediaStore.Files.FileColumns._ID))
                val bucketId: Long = originalBucketCursor.getLong(originalBucketCursor.getColumnIndex(COLUMN_BUCKET_ID))
                val bucketDisplayName: String = originalBucketCursor.getString(originalBucketCursor.getColumnIndex(COLUMN_BUCKET_DISPLAY_NAME))
                val mimeType: String = originalBucketCursor.getString(originalBucketCursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE)).orEmpty()
                val uri: Uri? = getUri(originalBucketCursor)
                val count: Int = originalBucketCursor.getInt(originalBucketCursor.getColumnIndex(COLUMN_COUNT))
                otherBucketCursor.addRow(arrayOf(fileId.toString(), bucketId.toString(), bucketDisplayName, mimeType, uri.toString(), count.toString()))
                totalCount += count
            }
        }

        if (originalBucketCursor?.moveToFirst() == true) {
            allBucketCoverUri = getUri(originalBucketCursor)
        }

        //添加第一条记录
        allBucketCursor.addRow(arrayOf(Bucket.BUCKET_ID_ALL, Bucket.BUCKET_ID_ALL, Bucket.BUCKET_NAME_ALL, null, allBucketCoverUri?.toString(), totalCount.toString()))

        //将"所有文件"与具体每一个Bucket的查询记录拼接成新的Cursor
        return MergeCursor(arrayOf<Cursor>(allBucketCursor, otherBucketCursor))
    }

    /**
     * Android 10 及以上版本 处理查询结果
     * 因为不能是由count(*) 需要手动计算每个Bucket的文件数量
     */
    private fun queryAndroid10AndAbove(originalBucketCursor: Cursor?): Cursor? {
        //"所有"分类的文件数量
        var totalCount = 0L
        //"所有"分类的封面图片Uri
        var allAlbumCoverUri: Uri? = null
        //记录第一条 表示所有媒体文件
        val allBucketCursor = MatrixCursor(COLUMNS)
        //记录2～N条 表示每一个Bucket
        val otherBucketCursor = MatrixCursor(COLUMNS)
        //一个保存每一个Bucket文件数量的Map
        val countMap: MutableMap<Long, Long> = mutableMapOf()

        /**
         * 手动模拟Group by操作
         * 使用originalBucketCursor遍历所有的文件 获取每一个文件的[COLUMN_BUCKET_ID]
         * 根据[COLUMN_BUCKET_ID]进行计数 保存到countMap中
         */
        if (originalBucketCursor != null) {
            while (originalBucketCursor.moveToNext()) {
                val bucketId: Long = originalBucketCursor.getLong(originalBucketCursor.getColumnIndex(COLUMN_BUCKET_ID))
                var count = countMap[bucketId]
                if (count == null) {
                    count = 1L
                } else {
                    count++
                }
                countMap[bucketId] = count
            }
        }

        /**
         * 遍历所有文件
         * 以[COLUMN_BUCKET_ID]为主键 找每个Bucket的第一个文件
         * 保存他们的信息 作为Bucket列表查询结果
         */
        if (originalBucketCursor != null) {
            if (originalBucketCursor.moveToFirst()) {
                allAlbumCoverUri = getUri(originalBucketCursor)
                val done: MutableSet<Long> = mutableSetOf()
                do {
                    val bucketId: Long = originalBucketCursor.getLong(originalBucketCursor.getColumnIndex(COLUMN_BUCKET_ID))
                    if (done.contains(bucketId)) {
                        continue
                    }
                    val fileId: Long = originalBucketCursor.getLong(originalBucketCursor.getColumnIndex(MediaStore.Files.FileColumns._ID))
                    val bucketDisplayName: String = originalBucketCursor.getString(
                            originalBucketCursor.getColumnIndex(COLUMN_BUCKET_DISPLAY_NAME)).orEmpty()
                    val mimeType: String = originalBucketCursor.getString(originalBucketCursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE))
                    val uri = getUri(originalBucketCursor)
                    val count = countMap[bucketId]!!
                    otherBucketCursor.addRow(arrayOf(fileId.toString(), bucketId.toString(), bucketDisplayName, mimeType, uri.toString(), count.toString()))
                    done.add(bucketId)
                    totalCount += count
                } while (originalBucketCursor.moveToNext())
            }
        }

        /**
         * 生成查询结果
         */
        allBucketCursor.addRow(arrayOf(Bucket.BUCKET_ID_ALL, Bucket.BUCKET_ID_ALL, Bucket.BUCKET_NAME_ALL, null, allAlbumCoverUri?.toString(), totalCount.toString()))

        return MergeCursor(arrayOf<Cursor>(allBucketCursor, otherBucketCursor))
    }

    companion object {
        private val QUERY_URI = MediaStore.Files.getContentUri("external")
        private const val BUCKET_ORDER_BY = "datetaken DESC"
        const val COLUMN_BUCKET_ID = "bucket_id"
        const val COLUMN_BUCKET_DISPLAY_NAME = "bucket_display_name"
        const val COLUMN_URI = "uri"
        const val COLUMN_COUNT = "count"

        /**
         * 向外返回的 Bucket 查询结果 Cursor 的列结构
         */
        private val COLUMNS = arrayOf(
                MediaStore.Files.FileColumns._ID,
                COLUMN_BUCKET_ID,
                COLUMN_BUCKET_DISPLAY_NAME,
                MediaStore.MediaColumns.MIME_TYPE,
                COLUMN_URI,
                COLUMN_COUNT)

        /**
         * Android 10 以下 查询的字段
         */
        private val PROJECTION = arrayOf(
                MediaStore.Files.FileColumns._ID,
                COLUMN_BUCKET_ID,
                COLUMN_BUCKET_DISPLAY_NAME,
                MediaStore.MediaColumns.MIME_TYPE,
                "COUNT(*) AS $COLUMN_COUNT")

        /**
         * Android 10 及以上 查询的字段
         */
        private val PROJECTION_29 = arrayOf(
                MediaStore.Files.FileColumns._ID,
                COLUMN_BUCKET_ID,
                COLUMN_BUCKET_DISPLAY_NAME,
                MediaStore.MediaColumns.MIME_TYPE)

        /**
         * 查询图片和视频
         */
        private const val SELECTION = "(${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE}=?) AND ${MediaStore.MediaColumns.SIZE}>0) GROUP BY (bucket_id"
        private const val SELECTION_29 = "(${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE}=?) AND ${MediaStore.MediaColumns.SIZE}>0"
        private val SELECTION_ARGS = arrayOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(), MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString())

        /**
         * 查询图片或视频
         * @see[getSelectionArgsForSingleMediaType] 使用此方法指定查询的媒体类型
         */
        private const val SELECTION_FOR_SINGLE_MEDIA_TYPE = "${MediaStore.Files.FileColumns.MEDIA_TYPE}=? AND ${MediaStore.MediaColumns.SIZE}>0) GROUP BY (bucket_id"
        private const val SELECTION_FOR_SINGLE_MEDIA_TYPE_29 = "${MediaStore.Files.FileColumns.MEDIA_TYPE}=? AND ${MediaStore.MediaColumns.SIZE}>0"
        /**
         * 生成SelectionArgs
         * @param [mediaType] 指定具体查询的媒体类型
         */
        private fun getSelectionArgsForSingleMediaType(mediaType: Int) = arrayOf(mediaType.toString())

        /**
         * 查询GIF文件
         */
        private const val SELECTION_FOR_SINGLE_MEDIA_GIF_TYPE = "${MediaStore.Files.FileColumns.MEDIA_TYPE}=? AND ${MediaStore.MediaColumns.SIZE}>0 AND ${MediaStore.MediaColumns.MIME_TYPE}=?) GROUP BY (bucket_id"
        private const val SELECTION_FOR_SINGLE_MEDIA_GIF_TYPE_29 = "${MediaStore.Files.FileColumns.MEDIA_TYPE}=? AND ${MediaStore.MediaColumns.SIZE}>0 AND ${MediaStore.MediaColumns.MIME_TYPE}=?"
        /**
         * 生成查询指定文件类型+gif文件的SelectionArgs
         * @param [mediaType] 指定具体查询的媒体类型
         */
        private fun getSelectionArgsForSingleMediaGifType(mediaType: Int) = arrayOf(mediaType.toString(), "image/gif")

        /**
         * 构建BucketLoader对象的方法
         */
        fun newInstance(context: Context): BucketLoader {

            var selection = getSelection()
            var selectionArgs = getSelectionArgs()



            return BucketLoader(context = context,
                    projection = getProjection(),
                    selection = selection,
                    selectionArgs = selectionArgs,
                    orderBy = BUCKET_ORDER_BY
            )

        }

        /**
         * 根据系统版本返回查询字段列表
         */
        private fun getProjection(): Array<String> {
            return if (beforeAndroid10()) {
                PROJECTION
            } else {
                PROJECTION_29
            }
        }

        /**
         * 生成查询条件
         */
        private fun getSelection(): String {
            return if (beforeAndroid10()) {
                when {
                    PickerSpec.isOnlyShowGif() -> {
                        SELECTION_FOR_SINGLE_MEDIA_GIF_TYPE
                    }
                    PickerSpec.isOnlyShowImages() -> {
                        SELECTION_FOR_SINGLE_MEDIA_TYPE
                    }
                    PickerSpec.isOnlyShowVideos() -> {
                        SELECTION_FOR_SINGLE_MEDIA_TYPE
                    }
                    PickerSpec.isOnlyShowAudios() -> {
                        SELECTION_FOR_SINGLE_MEDIA_TYPE
                    }
                    else -> {
                        SELECTION
                    }
                }
            } else {
                when {
                    PickerSpec.isOnlyShowGif() -> {
                        SELECTION_FOR_SINGLE_MEDIA_GIF_TYPE_29
                    }
                    PickerSpec.isOnlyShowImages() -> {
                        SELECTION_FOR_SINGLE_MEDIA_TYPE_29
                    }
                    PickerSpec.isOnlyShowVideos() -> {
                        SELECTION_FOR_SINGLE_MEDIA_TYPE_29
                    }
                    PickerSpec.isOnlyShowAudios() -> {
                        SELECTION_FOR_SINGLE_MEDIA_TYPE_29
                    }
                    else -> {
                        SELECTION_29
                    }
                }
            }
        }

        /**
         * 生成查询参数
         */
        private fun getSelectionArgs(): Array<String> {
            return when {
                PickerSpec.isOnlyShowGif() -> {
                    getSelectionArgsForSingleMediaGifType(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)
                }
                PickerSpec.isOnlyShowImages() -> {
                    getSelectionArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)
                }
                PickerSpec.isOnlyShowVideos() -> {
                    getSelectionArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO)
                }
                PickerSpec.isOnlyShowAudios() -> {
                    getSelectionArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO)
                }
                else -> {
                    SELECTION_ARGS
                }
            }
        }

        /**
         * 判断是否在Android10版本之前
         */
        private fun beforeAndroid10(): Boolean {
            return Build.VERSION.SDK_INT < Build.VERSION_CODES.Q
        }

        /**
         * 根据传入Cursor 生成对应文件的Uri
         */
        private fun getUri(cursor: Cursor): Uri? {
            val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID))

            val mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE))
            val contentUri: Uri
            contentUri = when {
                MimeType.isImage(mimeType) -> {
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                }
                MimeType.isVideo(mimeType) -> {
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                }
                else -> {
                    MediaStore.Files.getContentUri("external")
                }
            }
            return ContentUris.withAppendedId(contentUri, id)
        }
    }
}