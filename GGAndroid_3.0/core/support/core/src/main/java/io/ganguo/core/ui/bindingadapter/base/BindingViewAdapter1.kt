package io.ganguo.core.ui.bindingadapter.base

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * <pre>
 * author : leo
 * time   : 2018/10/20
 * desc   : BindingAdapter基类
</pre> *
 */
open class BindingViewAdapter {
    companion object {
        /**
         * xml绑定设置View 宽度
         *
         * @param view       对应view
         * @param bindHeight 高度px
         */
        @kotlin.jvm.JvmStatic
        @BindingAdapter("android:bind_view_height")
        fun onBindViewHeight(view: View, @Dimension bindHeight: Int) {
            val viewHeight: Int
            viewHeight = if (bindHeight > 0) {
                bindHeight
            } else if (bindHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
                ViewGroup.LayoutParams.MATCH_PARENT
            } else {
                ViewGroup.LayoutParams.WRAP_CONTENT
            }
            val params = view.layoutParams
            params.height = viewHeight
            view.layoutParams = params
        }

        /**
         * xml绑定设置View 宽度
         *
         * @param view      对应view
         * @param bindWidth 宽度px
         */
        @kotlin.jvm.JvmStatic
        @BindingAdapter("android:bind_view_width")
        fun onBindViewWidth(view: View, @Dimension bindWidth: Int) {
            val viewWidth: Int
            viewWidth = if (bindWidth > 0) {
                bindWidth
            } else if (bindWidth == ViewGroup.LayoutParams.MATCH_PARENT) {
                ViewGroup.LayoutParams.MATCH_PARENT
            } else {
                ViewGroup.LayoutParams.WRAP_CONTENT
            }
            val params = view.layoutParams
            params.width = viewWidth
            view.layoutParams = params
        }

        /**
         * xml绑定设置View背景颜色
         *
         * @param view     对应view
         * @param colorRes Drawable资源Id
         */
        @kotlin.jvm.JvmStatic
        @BindingAdapter("android:bind_bg_res")
        fun onBindBackgroundRes(view: View, @DrawableRes colorRes: Int) {
            view.setBackgroundResource(colorRes)
        }

        /**
         * xml绑定设置View背景颜色
         *
         * @param view  对应view
         * @param color int类型颜色值
         */
        @kotlin.jvm.JvmStatic
        @BindingAdapter("android:bind_bg_color")
        fun onBindBackgroundColor(view: View, @ColorInt color: Int) {
            view.setBackgroundColor(color)
        }

        /**
         * xml绑定设置View显示状态
         *
         * @param view       对应view
         * @param visibility 显示状态
         */
        @BindingAdapter("android:bind_visibility")
        fun onBindVisibility(view: View, visibility: Int) {
            when (visibility) {
                View.VISIBLE -> view.visibility = View.VISIBLE
                View.INVISIBLE -> view.visibility = View.INVISIBLE
                else -> view.visibility = View.GONE
            }
        }

        /**
         * xml绑定设置View显示状态
         *
         * @param view    对应view
         * @param visible 显示状态
         */
        @kotlin.jvm.JvmStatic
        @BindingAdapter("android:bind_visibility_boolean")
        fun onBindVisible(view: View, visible: Boolean) {
            view.visibility = if (visible) View.VISIBLE else View.GONE
        }

        /**
         * function:xml绑定设置View Margin
         *
         * @param view   对应view
         * @param margin 单位px
         */
        @BindingAdapter(value = ["android:bind_margin"], requireAll = false)
        fun onBindMargin(view: View?, margin: Int) {
            if (view == null) {
                return
            }
            onBindMargin(view, margin, margin, margin, margin)
        }

        /**
         * xml绑定设置View Margin
         *
         * @param view          对应view
         * @param paddingBottom 底部内边距，单位px
         * @param paddingLeft   左边内距，单位px
         * @param paddingRight  右边内距，单位px
         * @param paddingTop    顶部内边距，单位px
         */
        @kotlin.jvm.JvmStatic
        @BindingAdapter(value = ["android:bind_padding_left", "android:bind_padding_top", "android:bind_padding_right", "android:bind_padding_bottom"], requireAll = false)
        fun onBindPadding(view: View?, paddingLeft: Int, paddingTop: Int, paddingRight: Int, paddingBottom: Int) {
            if (view == null) {
                return
            }
            view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
        }

        /**
         * xml绑定View居中属性
         *
         * @param view    对应View
         * @param gravity 位置
         */
        @kotlin.jvm.JvmStatic
        @BindingAdapter(value = ["android:bind_view_gravity"], requireAll = false)
        fun onBindViewGravity(view: View?, gravity: Int) {
            if (view == null) {
                return
            }
            if (view is TextView) {
                view.gravity = gravity
            } else if (view is LinearLayout) {
                view.gravity = gravity
            }
        }

        /**
         * xml绑定设置View Margin
         *
         * @param view
         * @param marginBottom 底部外边距，单位px
         * @param marginLeft   左边外边距，单位px
         * @param marginRight  右边外边距，单位px
         * @param marginTop    顶部外边距，单位px
         */
        @kotlin.jvm.JvmStatic
        @BindingAdapter(value = ["android:bind_margin_left", "android:bind_margin_top", "android:bind_margin_right", "android:bind_margin_bottom"], requireAll = false)
        fun onBindMargin(view: View?, marginLeft: Int, marginTop: Int, marginRight: Int, marginBottom: Int) {
            if (view == null) {
                return
            }
            val layoutParams: MarginLayoutParams
            layoutParams = if (view.layoutParams == null) {
                MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT)
            } else if (view.layoutParams is LinearLayout.LayoutParams) {
                view.layoutParams as LinearLayout.LayoutParams
            } else if (view.layoutParams is RelativeLayout.LayoutParams) {
                view.layoutParams as RelativeLayout.LayoutParams
            } else if (view.layoutParams is ConstraintLayout.LayoutParams) {
                view.layoutParams as ConstraintLayout.LayoutParams
            } else if (view.layoutParams is GridLayoutManager.LayoutParams) {
                view.layoutParams as GridLayoutManager.LayoutParams
            } else if (view.layoutParams is RecyclerView.LayoutParams) {
                view.layoutParams as RecyclerView.LayoutParams
            } else if (view.layoutParams is FrameLayout.LayoutParams) {
                view.layoutParams as FrameLayout.LayoutParams
            } else {
                MarginLayoutParams(view.layoutParams)
            }
            setViewMargins(view, layoutParams, marginLeft, marginTop, marginRight, marginBottom)
        }

        /**
         * xml绑定设置View Margin
         *
         * @param view
         * @param layoutParams
         * @param marginBottom
         * @param marginLeft
         * @param marginRight
         * @param marginTop
         */
        fun setViewMargins(view: View, layoutParams: MarginLayoutParams, marginLeft: Int, marginTop: Int, marginRight: Int, marginBottom: Int) {
            if (marginLeft != layoutParams.leftMargin || marginTop != layoutParams.topMargin || marginRight != layoutParams.rightMargin || marginBottom != layoutParams.bottomMargin) {
                layoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom)
                view.layoutParams = layoutParams
            }
        }

        /**
         * xml 绑定 View 选中状态
         *
         * @param view
         * @param status 状态
         */
        @kotlin.jvm.JvmStatic
        @BindingAdapter(value = ["android:bind_selected"], requireAll = false)
        fun onBindViewSelected(view: View?, status: Boolean) {
            if (view == null) {
                return
            }
            view.isSelected = status
        }
    }
}