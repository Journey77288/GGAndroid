package io.ganguo.core.viewmodel.common.widget

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import io.ganguo.core.R
import io.ganguo.core.viewmodel.BaseViewModel
import io.ganguo.mvvm.adapter.ViewModelAdapter
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.support.recyclerview.manager.GridWrapperLayoutManager
import io.support.recyclerview.manager.LayoutManagerDelegate
import io.support.recyclerview.manager.LinearWrapperLayoutManager
import io.support.recyclerview.manager.StaggeredGridWrapperLayoutManager
import jp.wasabeef.recyclerview.animators.BaseItemAnimator

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/13
 *   @desc   : RecyclerVModel
 * </pre>
 * 用法:
 * 1、绑定include layout
 * 创建 RecyclerViewModel 实例与 layout 中的 R.layout.include_recycler 绑定
 * recyclerViewModel = RecyclerViewModel.linerLayout(getContext(),parent, LinearLayoutManager.VERTICAL);
 * ViewModelHelper.bind(getViewInterface().getBinding().includeRecycler, recyclerViewModel);
 *
 *
 * 2、直接与Activity、Fragment、Dialog、View绑定,设置为根布局
 * recyclerViewModel = initRecyclerViewModel();
 * ViewModelHelper.bind(this, recyclerViewModel);
 * Activity:
 * DataBindingUtil.setContentView(this, recyclerViewModel.getItemLayoutId());
 * Fragment:
 * public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 * return recyclerViewModel.getRootView();
 * }
 *
 * 3、Adapter Setting
 * [ViewModelAdapter] has realized [RecyclerDiffAdapter]
 * use：
 *   var viewModel = RecyclerVModel.create(parent: ViewModel<*>)
 *   viewModel.add(xxViewModel())
 *   viewModel.notifyDataSetChanged(true)
 *
 */
@Suppress("LeakingThis")
open class RecyclerVModel<V : ViewDataBinding, B : ViewInterface<V>>(parentContext: Context, @Dimension defaultHeight: Int) : BaseViewModel<B>() {
    override var layoutId: Int = R.layout.widget_recycler_view
    var adapter: ViewModelAdapter = ViewModelAdapter(parentContext, this)
    val recyclerView: RecyclerView
        get() = let {
            rootView as RecyclerView
        }
    var itemDecoration: RecyclerView.ItemDecoration? = null
    var layoutManagerDelegate: LayoutManagerDelegate = object : LayoutManagerDelegate {
        override fun create(): RecyclerView.LayoutManager {
            return LinearWrapperLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
    var spanSizeLookup: GridLayoutManager.SpanSizeLookup? = null
    var isOverScroll = true

    init {
        height(defaultHeight)
    }


    override fun onViewAttached(view: View) {
        initItemAnimator()
    }

    /**
     * 初始化动画
     */
    open fun initItemAnimator() {
        closeDefaultAnimator()
    }

    /**
     * 打开默认局部刷新动画
     */
    open fun openDefaultAnimator() {
        val animator = recyclerView.itemAnimator
        animator?.addDuration = 120
        animator?.changeDuration = 250
        animator?.moveDuration = 250
        animator?.removeDuration = 120
        animator?.let {
            it as SimpleItemAnimator
            it.supportsChangeAnimations = true
        }
    }

    /**
     * 关闭默认局部刷新动画
     */
    open fun closeDefaultAnimator() {
        val animator = recyclerView.itemAnimator
        animator?.addDuration = 0
        animator?.changeDuration = 0
        animator?.moveDuration = 0
        animator?.removeDuration = 0
        animator?.let {
            it as SimpleItemAnimator
            it.supportsChangeAnimations = false
        }
        recyclerView.itemAnimator = null
    }

    /**
     * 设置动画
     */
    open fun setAnimator(animator: BaseItemAnimator, duration:Long) {
        recyclerView.apply {
            itemAnimator = animator.apply {
                addDuration = duration
                removeDuration = duration
                moveDuration = duration
                changeDuration = duration
            }
        }
    }


    companion object {


        /**
         * 线性布局列表
         * @param context Context
         * @param orientation Int
         * @return RecyclerVModel<B>
         */
        fun <V : ViewDataBinding, B : ViewInterface<V>> create(context: Context, defaultHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT): RecyclerVModel<V, B> {
            return RecyclerVModel(context, defaultHeight)
        }


        /**
         * 线性布局列表
         * @param context Context
         * @param orientation Int
         * @return RecyclerVModel<B>
         */
        fun <V : ViewDataBinding, B : ViewInterface<V>> linerLayout(context: Context, orientation: Int, defaultHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT): RecyclerVModel<V, B> {
            return create<V, B>(context, defaultHeight)
                    .apply {
                        layoutManagerDelegate = object : LayoutManagerDelegate {
                            override fun create(): RecyclerView.LayoutManager {
                                return LinearWrapperLayoutManager(context, orientation, false)
                            }
                        }
                    }
        }

        /**
         *  网格布局列表
         * @param context Context
         * @param spanCount Int
         * @param orientation Int
         * @return RecyclerVModel<B>
         */
        fun <V : ViewDataBinding, B : ViewInterface<V>> gridLayout(context: Context, spanCount: Int, orientation: Int, defaultHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT): RecyclerVModel<V, B> {
            return create<V, B>(context, defaultHeight)
                    .apply {
                        layoutManagerDelegate = object : LayoutManagerDelegate {
                            override fun create(): RecyclerView.LayoutManager {
                                return GridWrapperLayoutManager(context, spanCount, orientation, false)
                            }
                        }
                    }
        }

        /**
         * 瀑布流布局列表
         * @param context Context
         * @param spanCount Int
         * @param orientation Int
         * @return RecyclerVModel<B>
         */
        fun <V : ViewDataBinding, B : ViewInterface<V>> staggeredGridLayout(context: Context, spanCount: Int, orientation: Int, defaultHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT): RecyclerVModel<V, B> {
            return create<V, B>(context, defaultHeight)
                    .apply {
                        layoutManagerDelegate = object : LayoutManagerDelegate {
                            override fun create(): RecyclerView.LayoutManager {
                                return StaggeredGridWrapperLayoutManager(spanCount, orientation)
                            }
                        }
                    }
        }
    }


}
