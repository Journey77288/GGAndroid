package io.ganguo.picker.ui.bucket

import android.content.Context
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import io.ganguo.picker.R
import io.ganguo.picker.core.bucket.Bucket
import io.ganguo.picker.core.entity.PickerSpec

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/06/05
 *     desc   : 展示Bucket列表时使用的Adapter
 * </pre>
 *
 * 展示Activity左上角Bucket列表时使用的Adapter
 * Bucket（相册）展示 是使用Spinner+ListView+CursorAdapter实现的
 * @param
 * @see
 * @author Raynor
 * @property [placeHolder] 相册列表的占位图
 */
class BucketAdapter private constructor(context: Context, cursor: Cursor?, autoRequery: Boolean) : CursorAdapter(context, cursor, autoRequery) {

    var placeHolder: Drawable? = null

    constructor(context: Context, cursor: Cursor?) : this(context, cursor, false) {
        //TODO 从配置文件中获取placeHolder
    }

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.bucket_list_item, parent, false)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val bucket = Bucket.valueOf(cursor)
        view?.findViewById<TextView>(R.id.title)?.text = bucket.getDisplayName(context)
        view?.findViewById<TextView>(R.id.count)?.text = bucket.count.toString()

        //TODO 使用注入的图片引擎显示封面图
        PickerSpec.imageEngine.loadThumbnail(context,
                context?.resources?.getDimensionPixelSize(R.dimen.media_grid_size)
                        ?: 1, placeHolder,
                view?.findViewById(R.id.cover),
                bucket.coverUri)
    }
}