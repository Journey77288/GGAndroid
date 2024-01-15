package io.ganguo.appcompat.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 *
 *
 * 修复使用DiffUtil异步比较数据后，切换到UI线程中刷新，出现Inconsistency detected. Invalid view holder adapter positionViewHolder错误问题
 * StaggeredGridLayoutManager包装类，重写@see [.supportsPredictiveItemAnimations]函数， return false。
 *
 * Created by leo on 2018/8/28.
 */
class StaggeredGridWrapperLayoutManager : StaggeredGridLayoutManager {
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {}
    constructor(spanCount: Int, orientation: Int) : super(spanCount, orientation) {}

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}