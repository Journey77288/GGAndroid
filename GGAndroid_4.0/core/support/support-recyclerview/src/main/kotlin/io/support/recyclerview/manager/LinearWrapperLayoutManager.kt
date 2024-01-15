package io.support.recyclerview.manager

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/12
 *     desc   : LinearWrapperLayoutManager
 * </pre>
 *
 * 修复使用DiffUtil异步比较数据后，切换到UI线程中刷新，出现Inconsistency detected. Invalid view holder adapter positionViewHolder错误问题
 * LinearLayoutManager包装类，重写@see [.supportsPredictiveItemAnimations]函数， return false。
 *
 * Created by leo on 2018/8/28.
 */
open class LinearWrapperLayoutManager : LinearLayoutManager {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}
