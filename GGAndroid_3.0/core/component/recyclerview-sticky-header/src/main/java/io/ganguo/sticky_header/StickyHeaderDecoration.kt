package io.ganguo.sticky_header

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.text.TextPaint
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/28
 *     desc   : 粘性悬浮Header
 * </pre>
 */
class StickyHeaderDecoration(var headerInterface: IStickyHeaderOnDrawListener) : RecyclerView.ItemDecoration() {
    private var adapter: List<*>? = null
    private var textPaint: Paint = TextPaint()
    private var bgPaint: Paint = Paint()

    init {
        textPaint.isAntiAlias = true
        textPaint.isDither = true

        bgPaint.isDither = true
        bgPaint.isAntiAlias = true
    }

    /**
     * 设置Header绘制范围
     * @param outRect Rect
     * @param view View
     * @param parent RecyclerView
     * @param state State
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        adapter = getAdapter(parent.adapter!!)
        var position = parent.getChildAdapterPosition(view)
        if (isDrawHeader(position)) {
            outRect.top = getStickyHeader(position)!!.stickyHeaderConfig.height
        } else {
            outRect.top = 0
        }
    }


    /**
     * 绘制所有头部内容
     * @param c Canvas
     * @param parent RecyclerView
     * @param state State
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        onDrawHeader(c, parent)
    }


    /**
     * 绘制覆盖item的内容
     * @param c Canvas
     * @param parent RecyclerView
     * @param state State
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        onDrawStickyHeader(c, parent)
    }


    /**
     * 绘制粘性头部
     * @param c Canvas
     * @param parent RecyclerView
     */
    private fun onDrawStickyHeader(c: Canvas, parent: RecyclerView) {
        var position = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        var header: IStickyHeader? = getStickyHeader(position) ?: return
        var left = parent.paddingLeft
        var right = parent.width + parent.paddingRight
        var rectF = RectF(left.toFloat(), 0f, right.toFloat(), header!!.stickyHeaderConfig.height.toFloat())
        onDrawHeader(c, header, rectF)
    }

    /**
     * 绘制头部Header 内容
     * @param c Canvas
     * @param parent RecyclerView
     */
    private fun onDrawHeader(c: Canvas, parent: RecyclerView) {
        var left = parent.paddingLeft
        var right = parent.width - parent.paddingRight
        var childCount = parent.childCount
        for (index in 0 until childCount) {
            var view = parent.getChildAt(index)
            var position = parent.getChildAdapterPosition(view)
            var header: IStickyHeader? = getStickyHeader(position) ?: continue
            if (isDrawHeader(position)) {
                var top = view.top - header!!.stickyHeaderConfig.height
                var bottom = view.top
                var rectF = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
                onDrawHeader(c, header, rectF)
                headerInterface.onDrawStickyHeader(position, header.stickyHeaderConfig.content)
            }
            if (position == (adapter!!.size - 1)) {
                headerInterface.onDrawStickyHeaderEnd(position)
            }
        }
    }


    /**
     * 绘制Header 内容
     * @param c Canvas
     * @param header IStickyHeader
     * @param rect 绘制范围
     */
    private fun onDrawHeader(c: Canvas, header: IStickyHeader, rect: RectF) {
        bgPaint.color = header.stickyHeaderConfig.bgColor
        c.drawRect(rect.left, rect.top, rect.right, rect.bottom, bgPaint)

        textPaint.color = header.stickyHeaderConfig.textColor
        textPaint.typeface = header.stickyHeaderConfig.textStyle
        var textSize = header.stickyHeaderConfig.textSize
        var textX = header.stickyHeaderConfig.left.toFloat()
        var textY = rect.bottom - header.stickyHeaderConfig.height / 2 + (abs(textPaint.ascent()) - textPaint.descent()) / 2
        textPaint.textSize = textSize
        c.drawText(header.stickyHeaderConfig.content, textX, textY, textPaint)
    }


    /**
     * 判断是否需要绘制头部
     * @param position Int
     * @return Boolean
     */
    private fun isDrawHeader(position: Int): Boolean {
        var current: IStickyHeader? = getStickyHeader(position) ?: return false
        var previous: IStickyHeader? = getStickyHeader(position - 1) ?: return true
        return current!!.stickyHeaderConfig.content != previous!!.stickyHeaderConfig.content
    }


    /**
     * 获取当前位置数据
     * @param position Int
     * @return IStickyHeader?
     */
    private fun getStickyHeader(position: Int): IStickyHeader? {
        if (position < 0 || position >= adapter!!.size) {
            return null
        }
        var item = adapter?.get(position)
        if (item is IStickyHeader) {
            return item
        }
        return null
    }

    private fun getAdapter(adapter: RecyclerView.Adapter<*>): List<*> {
        if (adapter !is List<*>) {
            throw RuntimeException("RecyclerView.Adapter must be implements List!!!")
        }
        return adapter
    }


    /**
     * 粘性头部，相关接口方法
     */
    interface IStickyHeader {
        /**
         * 粘性头部参数配置
         */
        val stickyHeaderConfig: StickyHeaderConfig
    }

}