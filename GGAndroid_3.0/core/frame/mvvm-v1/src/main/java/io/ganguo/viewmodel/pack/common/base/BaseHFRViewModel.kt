package io.ganguo.viewmodel.pack.common.base

import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ganguo.viewmodel.R
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.core.adapter.ViewModelAdapter
import io.ganguo.viewmodel.core.adapter.diffuitl.ViewModelDiffUtil
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.databinding.IncludeRecyclerBinding
import io.ganguo.viewmodel.pack.base.viewmodel.BaseSupportStateViewModel
import io.ganguo.viewmodel.pack.common.RecyclerViewModel

/**
 *
 *
 * HFRecyclerViewModel && HFSRecyclerViewModel 基类
 *
 * Created by leo on 2018/8/21.
 */
abstract class BaseHFRViewModel<B : ViewDataBinding, T : ViewInterface<B>> : BaseSupportStateViewModel<T>() {
    override val stateLayout: ViewGroup by lazy {
        statusContainer
    }

    /**
     * Getters RecyclerViewModel
     *
     * @return
     */
    private val recyclerVModel: RecyclerViewModel<IncludeRecyclerBinding, ViewInterface<IncludeRecyclerBinding>> by lazy {
        initRecyclerViewModel()
    }

    /**
     * Getters RecyclerView
     *
     * @return
     */
    val recyclerView: RecyclerView by lazy {
        recyclerVModel.recyclerView
    }

    /**
     * Getters Adapter
     *
     * @return
     */
    val adapter: ViewModelAdapter by lazy {
        recyclerVModel.adapter
    }
    var isAnimateLayoutChanges = ObservableBoolean(true)
    var isEnableHeaderElevation = ObservableBoolean(false)
    var isEnableFooterElevation = ObservableBoolean(false)


    /**
     * 页面背景颜色
     *
     * @return
     */
    open val backgroundColorRes: Int by lazy {
        R.color.transparent
    }

    /**
     * getters abstract ViewGroup
     *
     * @return
     */
    abstract val headerContainer: ViewGroup
    abstract val footerContainer: ViewGroup
    abstract val statusContainer: ViewGroup
    abstract val recyclerRootView: ViewDataBinding

    override fun onAttach() {
        super.onAttach()
        rootView.setBackgroundResource(backgroundColorRes)
        initHeader(headerContainer)
        initFooter(footerContainer)
        initRecyclerView()
    }

    /**
     * init RecyclerView
     */
    protected open fun initRecyclerView() {
        val adapter = ViewModelAdapter(context, this, ViewModelDiffUtil())
        recyclerVModel.setRecyclerAdapter(adapter)
        ViewModelHelper.bind(recyclerRootView, this, recyclerVModel)
        initRecyclerViewAnimator()
    }

    /**
     * 创建RecyclerViewViewModel
     */
    protected open fun initRecyclerViewModel(): RecyclerViewModel<IncludeRecyclerBinding, ViewInterface<IncludeRecyclerBinding>> {
        return RecyclerViewModel
                .linerLayout<IncludeRecyclerBinding, ViewInterface<IncludeRecyclerBinding>> (context, LinearLayoutManager.VERTICAL)
                .isOverScroll(false)
                .setViewHeight(ViewGroup.LayoutParams.MATCH_PARENT)
    }

    /**
     * init RecyclerView ItemAnimator
     */
    protected open fun initRecyclerViewAnimator() {
        val itemAnimator = recyclerView.itemAnimator
        val duration = 300
        itemAnimator!!.addDuration = duration.toLong()
        itemAnimator.changeDuration = duration.toLong()
        itemAnimator.moveDuration = duration.toLong()
        itemAnimator.removeDuration = duration.toLong()
    }

    /**
     * 隐藏Loading并判断是否显示EmptyView
     */
    open fun toggleEmptyView() {
        if (!isAttach) {
            return
        }
        if (adapter.itemCount == 0) {
            showEmptyView()
        } else {
            showContentView()
        }
    }

    /**
     * 是否开启View状态改变动画
     *
     * @param isAnimate 是否开启
     */
    fun setAnimateLayoutChanges(isAnimate: Boolean) {
        isAnimateLayoutChanges.set(isAnimate)
    }

    /**
     * 是否开启Footer底部阴影
     *
     * @param isEnable 是否开启
     */
    fun setEnableFooterElevation(isEnable: Boolean) {
        isEnableFooterElevation.set(isEnable)
    }

    /**
     * 是否开启header顶部阴影
     *
     * @param isEnable 是否开启
     */
    fun setEnableHeaderElevation(isEnable: Boolean) {
        isEnableHeaderElevation.set(isEnable)
    }


    /**
     * 初始化顶部菜单布局
     *
     * @param container
     */
    open fun initHeader(container: ViewGroup) {}

    /**
     * 初始化底部菜单布局
     *
     * @param container
     */
    open fun initFooter(container: ViewGroup) {}
}