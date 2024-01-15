package io.ganguo.picker.ui.media

import android.database.Cursor
import android.provider.MediaStore
import androidx.recyclerview.widget.RecyclerView

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/06/22
 *     desc   : TODO
 * </pre>
 *
 * PickerActivity内Recycler View Adapter的父类
 * 隐藏了一些基础逻辑 保证子类和父类的可读性
 * @param
 * @see
 * @author Raynor
 * @property [cursor]
 * @property [columnIndex]
 */
abstract class RecyclerViewCursorAdapter<VH : RecyclerView.ViewHolder> private constructor() : RecyclerView.Adapter<VH>() {
    private var cursor: Cursor? = null
    private var columnIndex = -1

    constructor(cursor: Cursor?) : this() {
        this.setHasStableIds(true)
        swapCursor(cursor)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        check(isCursorValid(cursor)) { "Cannot bind view holder when cursor is in invalid state." }

        check(cursor?.moveToPosition(position)
                ?: false) { ("Could not move cursor to position $position when trying to bind view holder") }

        //检查数据合法性之后 委托给真正处理ViewHolder绑定的方法处理
        onBindViewHolder(holder, cursor)
    }

    /**
     * 要求子类实现的 绑定ViewHolder的方法
     */
    abstract fun onBindViewHolder(holder: VH, cursor: Cursor?)

    override fun getItemCount(): Int {
        return if (isCursorValid(cursor)) {
            cursor!!.count
        } else {
            0
        }
    }

    override fun getItemViewType(position: Int): Int {
        check(cursor?.moveToPosition(position)
                ?: false) { ("Could not move cursor to position $position when trying to bind view holder") }

        //检查数据合法性之后 委托给真正处理计算视图类型的方法
        return getItemViewType(position, cursor!!)
    }

    /**
     * 要求子类实现的 计算视图类型的方法
     */
    abstract fun getItemViewType(position: Int, cursor: Cursor): Int


    /**
     * 返回RecyclerView中指定position的Item的"stable ID"
     * 实际实现是通过游标和[columnIndex] 获取[MediaStore.Files.FileColumns._ID]
     */
    override fun getItemId(position: Int): Long {
        check(isCursorValid(cursor)) { "Cannot bind view holder when cursor is in invalid state." }

        check(cursor?.moveToPosition(position)
                ?: false) { ("Could not move cursor to position $position when trying to bind view holder") }

        return cursor!!.getLong(columnIndex)
    }

    /**
     * 更新游标
     * 其实就是更新了数据源
     * @param [cursor] 游标
     */
    fun swapCursor(cursor: Cursor?) {
        if (cursor == this.cursor) {
            return
        }

        if (cursor != null) {
            this.cursor = cursor
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            notifyDataSetChanged()
        } else {
            notifyItemRangeRemoved(0, itemCount)
            this.cursor = null
            columnIndex = -1
        }
    }

    /**
     * 检查游标是否合法
     * @param [cursor] 游标
     */
    open fun isCursorValid(cursor: Cursor?): Boolean {
        return cursor != null && !cursor.isClosed
    }
}