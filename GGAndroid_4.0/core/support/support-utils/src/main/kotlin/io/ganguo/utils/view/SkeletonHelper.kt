package io.ganguo.utils.view

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.ViewSkeletonScreen
import io.ganguo.utils.R

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/04/26
 *     desc   : 构建骨架图工具类
 * </pre>
 * @property [DEFAULT_ANGLE] 骨架图动画默认展示角度
 * @property [DEFAULT_DURATION] 骨架图动画默认展示时长
 */
object SkeletonHelper {
    private const val DEFAULT_ANGLE = 15
    private const val DEFAULT_DURATION = 2000

    /**
     * Skeleton bind View
     * 注：需要在外部调用时，设置对应的布局文件
     *
     * @param bindView View  绑定view
     * @param loadView View  加载骨架图view
     * @param angle Int 骨架图动画展示的角度
     * @param duration Int 骨架图动画展示的时长
     * @param skeletonColorRes Int 骨架图颜色值
     * @return ViewSkeletonScreen.Builder?
     */
    fun createSkeleton(bindView: View,
                       @LayoutRes loadView: Int,
                       @IntRange(from = 0, to = 30) angle: Int = DEFAULT_ANGLE,
                       duration: Int = DEFAULT_DURATION,
                       @ColorRes skeletonColorRes: Int = R.color.color_F2F2F2): ViewSkeletonScreen {
        return Skeleton.bind(bindView)
                .shimmer(true)
                .load(loadView)
                .color(skeletonColorRes)
                .angle(angle)
                .duration(duration)
                .show()
    }

    /**
     * Skeleton bind RecyclerView
     *
     * @param recyclerView RecyclerView
     * @param itemLayoutRes Int Item的占位图LayoutRes
     * @param itemCount Int Item数量
     * @param angle Int 骨架图动画展示的角度
     * @param duration Int 骨架图动画展示的时长
     * @param skeletonColorRes Int 骨架图颜色值
     * @return
     */
    fun createSkeletonForRecyclerView(recyclerView: RecyclerView,
                                      @LayoutRes itemLayoutRes: Int,
                                      itemCount: Int = 4,
                                      @IntRange(from = 0, to = 30) angle: Int = DEFAULT_ANGLE,
                                      duration: Int = DEFAULT_DURATION,
                                      @ColorRes skeletonColorRes: Int = R.color.color_F2F2F2): RecyclerViewSkeletonScreen {
        return Skeleton.bind(recyclerView)
                .adapter(recyclerView.adapter)
                .load(itemLayoutRes)
                .shimmer(true)
                .color(skeletonColorRes)
                .count(itemCount)
                .angle(angle)
                .duration(duration)
                .frozen(false)
                .show()
    }
}