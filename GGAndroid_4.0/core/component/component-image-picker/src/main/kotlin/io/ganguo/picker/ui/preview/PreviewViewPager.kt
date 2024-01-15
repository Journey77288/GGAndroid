package io.ganguo.picker.ui.preview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/07/22
 *     desc   : 图片预览Activity使用的View Pager
 * </pre>
 *
 * 图片预览Activity使用的View Pager
 * @param
 * @see
 * @author Raynor
 * @property
 */
class PreviewViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
	override fun onTouchEvent(ev: MotionEvent?): Boolean {
		return try {
			super.onTouchEvent(ev)
		} catch (e: IllegalArgumentException) {
			e.printStackTrace()
			false
		}
	}

	override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
		return try {
			super.onInterceptTouchEvent(ev)
		} catch (e: IllegalArgumentException) {
			e.printStackTrace()
			false
		}
	}
}