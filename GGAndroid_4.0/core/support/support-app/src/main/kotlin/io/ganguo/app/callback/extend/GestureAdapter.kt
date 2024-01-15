package io.ganguo.app.callback.extend

import android.view.GestureDetector
import android.view.MotionEvent


/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 手势适配器
 * </pre>
 */
@Suppress("MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")
class GestureAdapter : GestureDetector.OnGestureListener {
    /**
     * 按下
     *
     * @param e
     * @return
     */
    override fun onDown(e: MotionEvent): Boolean {
        return false
    }

    /**
     * 按下
     *
     * @param e
     */
    override fun onShowPress(e: MotionEvent) {
    }

    /**
     * 单击
     *
     * @param e
     * @return
     */
    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    /**
     * 滑动
     *
     * @param e1
     * @param e2
     * @param distanceX
     * @param distanceY
     * @return
     */
    override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        return false
    }

    /**
     * 长按
     *
     * @param e
     */
    override fun onLongPress(e: MotionEvent) {
    }

    /**
     * 手势滑动
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        if (e1 == null) {
            return false
        }
        val up = e1.y - e2.y
        val bottom = e2.y - e1.y
        val left = e1.x - e2.x
        val right = e2.x - e1.x
        if (up > bottom && up > left && up > right) {
            // 向上手势
            return onFlingUp(e1, e2, velocityX, velocityY)
        } else if (bottom > up && bottom > left && bottom > right) {
            // 向下手势
            return onFlingBottom(e1, e2, velocityX, velocityY)
        } else if (left > up && left > bottom && left > right) {
            // 向左手势
            return onFlingLeft(e1, e2, velocityX, velocityY)
        } else if (right > up && right > bottom && right > left) {
            // 向右手势
            return onFlingRight(e1, e2, velocityX, velocityY)
        }
        return false
    }

    /**
     * 向左手势
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    fun onFlingLeft(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    /**
     * 向右手势
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    fun onFlingRight(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    /**
     * 向上手势
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    fun onFlingUp(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    /**
     * 向下手势
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    fun onFlingBottom(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        return false
    }
}
