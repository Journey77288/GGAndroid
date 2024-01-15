package io.ganguo.core.viewmodel.common.component

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.blankj.utilcode.util.BarUtils
import io.ganguo.core.databinding.ComponentHeaderTitleBinding
import io.ganguo.core.viewmodel.BaseViewModel
import io.ganguo.core.viewmodel.common.widget.ImageViewModel
import io.ganguo.core.viewmodel.common.widget.TextViewModel
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.resources.R
import io.ganguo.resources.ResourcesHelper
import io.image.enums.ImageShapeType

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/10/29
 *   @desc   : HeaderContainer ViewModel
 * </pre>
 * @see isExtendedStatusBar 是否延伸到状态栏，针对配置了[BarUtils.transparentStatusBar]或其他场景，会默认给增加状态栏高度
 */
open class HeaderTitleVModel(private val isExtendedStatusBar: Boolean = false) : BaseViewModel<ViewInterface<ComponentHeaderTitleBinding>>() {
    override val layoutId: Int = io.ganguo.core.R.layout.component_header_title
    val enableElevation = ObservableBoolean()
    val elevationSize = ObservableInt()
    val leftItems = ObservableField<MutableList<BaseViewModel<*>>>(arrayListOf())
    val rightItems = ObservableField<MutableList<BaseViewModel<*>>>(arrayListOf())
    val centerItems = ObservableField<MutableList<BaseViewModel<*>>>(arrayListOf())


    init {
        setDefaultSize()
        enableElevation(false)
        enabled(true)
        clickable(true)
        visible(View.VISIBLE)
    }

    override fun onViewAttached(view: View) {

    }


    /**
     * 设置Header默认尺寸
     */
    private fun setDefaultSize() {
        var headerHeight = getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_50)
        if (isExtendedStatusBar) {
            val statusBarHeight = BarUtils.getStatusBarHeight()
            paddingTop(statusBarHeight)
            headerHeight += BarUtils.getStatusBarHeight()
        }
        width(ViewGroup.LayoutParams.MATCH_PARENT)
        height(headerHeight)
    }

    /**
     * 是否开启ViewGroup阴影（需要在布局中绑定[enableElevation]属性方可有效）
     * @param enable Boolean
     */
    open fun enableElevation(enable: Boolean, elevationSize: Int = getDimensionPixelOffset(R.dimen.dp_4)) {
        this.enableElevation.set(enable)
        this.elevationSize.set(elevationSize)
    }

    /**
     * 添加左侧菜单ViewModel
     * @param item Array<out BaseViewModel<*>>
     */
    fun appendLeftItem(vararg item: BaseViewModel<*>) {
        leftItems.get()?.addAll(item)
    }

    /**
     * 添加右侧菜单ViewModel
     * @param item Array<out BaseViewModel<*>>
     */
    fun appendRightItem(vararg item: BaseViewModel<*>) {
        rightItems.get()?.addAll(item)
    }

    /**
     * 添加中间菜单ViewModel
     * @param item Array<out BaseViewModel<*>>
     */
    fun appendCenterItem(vararg item: BaseViewModel<*>) {
        centerItems.get()?.addAll(item)
    }


    companion object {


        /**
         * sample page title
         * @param activity Activity
         * @param title String
         * @return HeaderTitleVModel
         */
        @JvmStatic
        fun sampleTitleVModel(activity: Activity, title: String): HeaderTitleVModel = let {
            HeaderTitleVModel(false).apply {
                appendCenterItem(HeaderTitleVModel.textItemVModel(title)
                        .apply {
                            textColor(Color.WHITE)
                        })
                appendLeftItem(HeaderTitleVModel.backImageViewModel(
                        activity, io.ganguo.core.R.drawable.ic_back))
                backgroundColor(getColor(R.color.colorPrimary))
            }
        }


        /**
         *  标题栏 返回 ViewModel
         * @param activity Activity?
         * @param textRes Int
         * @return TextViewModel
         */
        @JvmStatic
        fun backTextItemVModel(activity: Activity?, textRes: Int): TextViewModel = let {
            backTextItemVModel(activity, ResourcesHelper.getString(textRes))
        }

        /**
         *  标题栏 文字返回菜单 ViewModel
         * @param activity Activity?
         * @param text String
         * @return TextViewModel
         */
        @JvmStatic
        fun backTextItemVModel(activity: Activity?, text: String, @DrawableRes leftRes: Int = io.ganguo.core.R.drawable.ic_back): TextViewModel = let {
            TextViewModel().apply {
                gravity(Gravity.CENTER)
                text(text)
                height(ViewGroup.LayoutParams.MATCH_PARENT)
                width(ViewGroup.LayoutParams.WRAP_CONTENT)
                paddingHorizontalRes(R.dimen.dp_15)
                drawableLeftRes(leftRes)
                textSizeRes(R.dimen.font_13)
                drawablePaddingRes(R.dimen.dp_10)
                textColor(Color.WHITE)
                click {
                    activity?.finish()
                }
            }
        }


        /**
         * 标题文字 ViewModel
         * @param textRes String
         * @return TextViewModel
         */
        @JvmStatic
        fun textItemVModel(@StringRes textRes: Int): TextViewModel = let {
            textItemVModel(ResourcesHelper.getString(textRes))
        }

        /**
         * 标题栏 title ViewModel
         * @param text String
         * @return TextViewModel
         */
        @JvmStatic
        fun textItemVModel(text: String): TextViewModel = let {
            TextViewModel().apply {
                gravity(Gravity.CENTER)
                text(text)
                height(ViewGroup.LayoutParams.MATCH_PARENT)
                width(ViewGroup.LayoutParams.WRAP_CONTENT)
                paddingHorizontalRes(R.dimen.dp_15)
                textSizeRes(R.dimen.font_16)
                textColorRes(R.color.white)
            }
        }


        /**
         * 标题 图片返回菜单 ViewModel
         * @param activity Activity?
         * @param iconRes int
         * @return ImageViewModel
         */
        fun backImageViewModel(activity: Activity?, @DrawableRes iconRes: Int = io.ganguo.core.R.drawable.ic_back): ImageViewModel = let {
            ImageViewModel(ImageShapeType.SQUARE)
                    .apply {
                        width(ResourcesHelper.getDimensionPixelOffset(R.dimen.dp_45))
                        height(ViewGroup.LayoutParams.MATCH_PARENT)
                        paddingHorizontalRes(R.dimen.dp_15)
                        drawableRes(iconRes)
                        click {
                            activity?.finish()
                        }
                    }
        }


        /**
         * 标题 按钮 ViewModel
         *
         * @param text
         * @param callback
         * @return TextViewModel
         */
        @JvmStatic
        fun textButtonViewModel(text: String, callback: ((View) -> Unit)): TextViewModel = let {
            TextViewModel().apply {
                gravity(Gravity.CENTER)
                text(text)
                height(ViewGroup.LayoutParams.MATCH_PARENT)
                width(ViewGroup.LayoutParams.WRAP_CONTENT)
                paddingHorizontalRes(R.dimen.dp_15)
                textSizeRes(R.dimen.font_13)
                textColorRes(R.color.white)
                click(callback)
            }
        }
    }

}
