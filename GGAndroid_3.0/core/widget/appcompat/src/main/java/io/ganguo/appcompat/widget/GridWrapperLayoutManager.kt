package io.ganguo.appcompat.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager

/**
 *
 *
 * 修复使用DiffUtil异步比较数据后，切换到UI线程中刷新，出现Inconsistency detected. Invalid view holder adapter positionViewHolder错误问题
 * GridLayoutManager包装类，重写@see [.supportsPredictiveItemAnimations]函数， return false。
 *
 * Created by leo on 2018/8/28.
 */
class GridWrapperLayoutManager : GridLayoutManager {
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {}
    constructor(context: Context?, spanCount: Int) : super(context, spanCount) {}
    constructor(context: Context?, spanCount: Int, orientation: Int, reverseLayout: Boolean) : super(context, spanCount, orientation, reverseLayout) {}

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}