package io.ganguo.picker.core.bucket

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Parcelable
import io.ganguo.picker.R
import io.ganguo.picker.core.loader.BucketLoader
import kotlinx.parcelize.Parcelize

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/06/05
 *     desc   : Bucket 实体类
 * </pre>
 *
 * Bucket 实体类
 * 类似于 "相册"
 * @param
 * @see
 * @author Raynor
 * @property
 */
@Parcelize
data class Bucket(
        val id: String,
        val coverUri: Uri,
        private val displayName: String,
        val count: Long
) : Parcelable {

    /**
     * 获取 Display
     */
    fun getDisplayName(context: Context?): String {
        return if (isAll()) {
            context?.getString(R.string.bucket_name_all) ?: ""
        } else {
            displayName
        }
    }

    /**
     * 判断当前Bucket是否表示"所有图片"
     */
    fun isAll(): Boolean = id == BUCKET_ID_ALL

    /**
     * 判断当前Bucket中媒体资源的数量是否为0
     */
    fun isEmpty(): Boolean = count == 0L

    companion object {
        const val BUCKET_ID_ALL = "-1"
        const val BUCKET_NAME_ALL = "All"

        /**
         * 将游标转转换为Bucket对象
         * @param [cursor] 查询返回结果游标
         */
        fun valueOf(cursor: Cursor?): Bucket {
            if (cursor == null) {
                throw IllegalArgumentException("cursor must not to be null")
            }

            val column = cursor.getString(cursor.getColumnIndex(BucketLoader.COLUMN_URI)).orEmpty()
            return Bucket(
                    cursor.getString(cursor.getColumnIndex(BucketLoader.COLUMN_BUCKET_ID)),
                    Uri.parse(column),
                    cursor.getString(cursor.getColumnIndex(BucketLoader.COLUMN_BUCKET_DISPLAY_NAME)),
                    cursor.getLong(cursor.getColumnIndex(BucketLoader.COLUMN_COUNT))
            )
        }
    }
}