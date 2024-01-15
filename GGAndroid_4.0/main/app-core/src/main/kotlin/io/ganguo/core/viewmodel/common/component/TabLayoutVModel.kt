package io.ganguo.core.viewmodel.common.component

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.databinding.ViewDataBinding
import io.ganguo.core.R
import io.ganguo.core.databinding.WidgetTabLayoutBinding
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.tab.callback.OnTabChooseListener
import io.ganguo.tab.callback.OnTabScrollListener
import io.ganguo.tab.callback.TabLayoutMediator
import io.ganguo.tab.model.TabModel


/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/14
 *   @desc   : WidTabLayoutVModel
 * </pre>
 */
class TabLayoutVModel private constructor(var builder: Builder) : ViewModel<ViewInterface<WidgetTabLayoutBinding>>() {
    override val layoutId: Int by lazy {
        R.layout.widget_tab_layout
    }
    val model: TabModel by lazy {
        createTabModel()
    }

    override fun onViewAttached(view: View) {
    }

    @ColorInt
    fun getBackgroundColor(): Int {
        return builder.backgroundColor
    }


    fun getTabMediator(): TabLayoutMediator {
        return viewIF.binding.tab
    }


    @Dimension
    fun getTabHeight(): Int {
        return builder.tabHeight
    }

    /**
     * create TabModel
     */
    private fun createTabModel(): TabModel {
        val views: MutableList<View> = mutableListOf()
        builder.itemVModels.forEach { viewModel ->
            ViewModelHelper.inflate<ViewDataBinding>(context, viewModel.layoutId).let {
                ViewModelHelper.bind(it, this, viewModel)
                views.add(it.root)
            }
        }
        return TabModel()
                .addTabViews(views)
                .setDistributeEvenly(builder.isDistributeEvenly)
                .setIndicatorColors(*builder.indicatorColor)
                .setOnTabScrollListener(builder.scrollListener)
                .setSelectedIndicatorVisible(builder.indicatorVisible)
                .setSelectedIndicatorHeight((if (builder.indicatorVisible) builder.indicatorHeight.toFloat() else 0f))
                .setSelectedIndicatorWidth((if (builder.indicatorVisible) builder.indicatorWidth.toFloat() else 0f))
                .setSelectedIndicatorRadius(builder.indicatorRadius)
                .setTabChooseListener(builder.chooseListener)
                .setIndicatorWidthWrapContent(builder.indicatorWidthWrapContent)
    }

    /**
     * add tab
     *
     * @param viewModel ViewModel<*>
     */
    fun addTab(viewModel: ViewModel<*>) {
        val data = ViewModelHelper.inflate<ViewDataBinding>(context, viewModel.layoutId).let {
            ViewModelHelper.bind(it, this, viewModel)
        }
        model.addTabView(data.rootView)
        notifyChange()
    }

    /**
     * add tab in specify index
     *
     * @param index Int
     * @param viewModel ViewModel<*>
     */
    fun addTab(index: Int, viewModel: ViewModel<*>) {
        val data = ViewModelHelper.inflate<ViewDataBinding>(context, viewModel.layoutId).let {
            ViewModelHelper.bind(it, this, viewModel)
        }
        model.addTabView(index, data.rootView);
        notifyChange()
    }

    /**
     * remove tab
     * @param position Int
     */
    fun removeTab(position: Int) {
        getTabMediator().onPageRemove(position)
    }

    /**
     * Builder 参数配置
     * @property layoutId Int
     * @property tabHeight Int tab栏高度
     * @property indicatorWidth Int 指示器宽度
     * @property indicatorHeight Int 指示器高度
     * @property indicatorColor IntArray 指示器颜色
     * @property backgroundColor Int tab栏背景颜色
     * @property indicatorVisible Boolean  是否显示指示器
     * @property isDistributeEvenly Boolean 是否平均分布父容器
     * @property chooseListener TabChooseListener?
     * @property scrollListener OnTabScrollListener?
     * @property indicatorWrapContent Boolean 指示器宽度是否是自适应
     * @property indicatorRadius Int 选中指示器圆角半径
     */
    @Suppress("MemberVisibilityCanBePrivate")
    class Builder {
        internal var itemVModels: MutableList<ViewModel<*>> = mutableListOf()

        @Dimension
        internal var tabHeight = ViewGroup.LayoutParams.WRAP_CONTENT

        @Dimension
        internal var indicatorWidth = 100

        @Dimension
        internal var indicatorHeight = 10

        @ColorInt
        internal var indicatorColor = intArrayOf(Color.RED)

        @ColorInt
        internal var backgroundColor = Color.TRANSPARENT
        internal var indicatorVisible = true
        internal var isDistributeEvenly = true
        internal var chooseListener: OnTabChooseListener? = null
        internal var scrollListener: OnTabScrollListener? = null
        internal var indicatorWidthWrapContent = false
        internal var indicatorRadius: Float = 0f

        /**
         * add TabLayout Item ViewModel
         * @param item ViewModel<*>
         * @return Builder
         */
        fun appendViewModel(item: ViewModel<*>): Builder = also {
            itemVModels.add(item)
        }

        /**
         * add TabLayout Item ViewModel
         * @param items List<ViewModel<*>>
         * @return Builder
         */
        fun appendViewModels(items: List<ViewModel<*>>): Builder = also {
            itemVModels.addAll(items)
        }

        /**
         * indicator Radius
         * @param radius Int
         * @return Builder
         */
        fun indicatorRadius(radius: Float): Builder = also {
            indicatorRadius = radius
        }

        fun indicatorWrapContent(isWrapContent: Boolean): Builder = also {
            indicatorWidthWrapContent = isWrapContent
        }

        fun tabHeight(height: Int): Builder = also {
            tabHeight = height
        }

        fun indicatorWidth(width: Int): Builder = also {
            indicatorWidth = width
        }

        fun indicatorHeight(height: Int): Builder = also {
            indicatorHeight = height
        }

        fun indicatorColor(vararg colors: Int): Builder = also {
            indicatorColor = colors
        }

        fun backgroundColor(@ColorInt color: Int): Builder = also {
            backgroundColor = color
        }

        fun indicatorVisible(visible: Boolean): Builder = also {
            indicatorVisible = visible
        }


        fun distributeEvenly(evenly: Boolean): Builder = also {
            isDistributeEvenly = evenly
        }


        fun chooseListener(listener: OnTabChooseListener): Builder = also {
            chooseListener = listener
        }


        fun scrollListener(listener: OnTabScrollListener) = also {
            scrollListener = listener
        }

        fun build(): TabLayoutVModel {
            return TabLayoutVModel(this)
        }

    }

}
