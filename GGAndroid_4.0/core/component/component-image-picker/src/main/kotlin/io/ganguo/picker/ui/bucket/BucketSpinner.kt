package io.ganguo.picker.ui.bucket

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.AdapterView
import android.widget.CursorAdapter
import android.widget.ListPopupWindow
import android.widget.TextView
import io.ganguo.picker.R
import io.ganguo.picker.core.bucket.Bucket

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/06/05
 *     desc   : 用来显示Bucket(相册)列表的工具类
 * </pre>
 *
 * 用来显示Bucket(相册)列表的工具类
 * 说是Spinner 实际是封装了ListPopupWindow的工具类
 * @param
 * @see
 * @author Raynor
 * @property [cursorAdapter] ListPopupWindow使用的Adapter
 * @property [selectedTextView] Activity的Toolbar中的标题TextView
 * @property [popupWindow] 实际用来显示的ListPopupWindow
 * @property [itemSelectedListener] 外部(Activity)传入的OnItemClickListener
 */
class BucketSpinner(context: Context) {
    private lateinit var cursorAdapter: CursorAdapter
    private lateinit var selectedTextView: TextView
    private val popupWindow: ListPopupWindow = ListPopupWindow(context, null, R.attr.listPopupWindowStyle)
    var itemSelectedListener: AdapterView.OnItemSelectedListener? = null

    init {
        popupWindow.isModal = true

        //设置尺寸
        val density = context.resources.displayMetrics.density
        popupWindow.setContentWidth((216 * density).toInt())
        popupWindow.horizontalOffset = (16 * density).toInt()
        popupWindow.verticalOffset = (-48 * density).toInt()

        //设置ListPopupWindow的点击监听
        popupWindow.setOnItemClickListener { parent, view, position, id ->
            onItemSelected(context, position)
            if (itemSelectedListener != null) {
                itemSelectedListener?.onItemSelected(parent, view, position, id)
            }
        }
    }

    /**
     * ListPopupWindow内部item 点击回调
     * 主要是设置[selectedTextView]显示的标题
     */
    private fun onItemSelected(context: Context, position: Int) {
        popupWindow.dismiss()

        val cursor = cursorAdapter.cursor
        cursor.moveToPosition(position)
        val bucket = Bucket.valueOf(cursor)
        val displayName = bucket.getDisplayName(context)
        if (selectedTextView.visibility == View.VISIBLE) {
            selectedTextView.text = displayName
        } else {
            selectedTextView.alpha = 0.0f
            selectedTextView.visibility = View.VISIBLE
            selectedTextView.text = displayName
            selectedTextView.animate().alpha(1.0f).setDuration(context.resources.getInteger(
                    android.R.integer.config_longAnimTime).toLong()).start()
        }
    }

    /**
     * 在Activity加载完Bucket查询结果后 使用此方法设置选中的position
     * @param [context] Activity上下文
     * @param [position] 选中的position
     */
    fun setSelection(context: Context, position: Int) {
        popupWindow.setSelection(position)
        onItemSelected(context, position)
    }

    /**
     * 设置ListPopupWindow使用的CursorAdapter
     * @param [adapter] BucketAdapter
     */
    fun setAdapter(adapter: CursorAdapter) {
        cursorAdapter = adapter
        popupWindow.setAdapter(adapter)
    }

    fun setPopupAnchorView(view: View?) {
        popupWindow.anchorView = view
    }

    /**
     * 设置Activity Toolbar的标题
     * @param[textView] Toolbar的TextView
     * 在ListPopupWindow内item点击之后 修改标题
     */
    @SuppressLint("ClickableViewAccessibility")
    fun setSelectedTextView(textView: TextView) {
        selectedTextView = textView

        //TODO tint dropdown arrow icon
//        val drawables: Array<Drawable> = selectedTextView.compoundDrawables
//        val right = drawables[2]
//        val ta: TypedArray = selectedTextView.getContext().getTheme().obtainStyledAttributes(intArrayOf(R.attr.album_element_color))
//        val color = ta.getColor(0, 0)
//        ta.recycle()
//        right.setColorFilter(color, PorterDuff.Mode.SRC_IN)

        selectedTextView.visibility = View.GONE
        selectedTextView.setOnClickListener(View.OnClickListener { v ->
            val itemHeight = v.resources.getDimensionPixelSize(R.dimen.bucket_item_height)
            popupWindow.height = if (cursorAdapter.count > MAX_SHOWN_COUNT) itemHeight * MAX_SHOWN_COUNT else itemHeight * cursorAdapter.count
            popupWindow.show()
        })
        selectedTextView.setOnTouchListener(popupWindow.createDragToOpenListener(textView))

    }

    companion object {
        const val MAX_SHOWN_COUNT = 6
    }

}