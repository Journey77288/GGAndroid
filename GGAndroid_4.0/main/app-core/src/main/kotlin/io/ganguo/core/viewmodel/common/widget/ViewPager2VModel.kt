package io.ganguo.core.viewmodel.common.widget

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.CollectionUtils
import io.ganguo.core.R
import io.ganguo.core.databinding.WidgetViewPagerBinding
import io.ganguo.core.viewmodel.BaseViewModel
import io.ganguo.mvvm.adapter.ViewModelAdapter
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.utils.view.setOverscrollMode
import io.support.recyclerview.adapter.diffuitl.IDiffComparator

/**
 * <pre>
 *   @author : leo
 *   @time : 2020/07/14
 *   @desc : ViewPager2VModel
 * </pre>
 * use:
 *   @param PageViewModel must implement the [IDiffComparator] interface
 *   @param adapter In general, you can just use [ViewModelAdapter]
 *
 *    var viewModel=ViewPager2VModel(ViewModelAdapter(parent, ViewModelDiffHandler()))
 *
 *    viewModel.add(xxPageViewModel())
 *    viewModel.add(xxPageViewModel())
 *    or
 *    viewModel.addAll(mutableListOf(
 *                   xxPageHomeVModel(),
 *                   xxPageHomeVModel(),
 *                   xxPageHomeVModel(),
 *                   xxPageHomeVModel()))
 *
 *    viewModel.notifyDataSetChanged()
 *
 */
open class ViewPager2VModel<V : ViewInterface<WidgetViewPagerBinding>> constructor(
        val adapter: ViewModelAdapter) : BaseViewModel<V>(), IDiffComparator<MutableList<ViewModel<*>>> {
    override val layoutId: Int = R.layout.widget_view_pager

    protected open var defaultPosition: Int = 0
    protected open var offscreenPageLimit = 1
    protected open var isUserInputEnabled = true
    protected open var pageTransformer: ViewPager2.PageTransformer? = null
    protected open var itemDecoration: RecyclerView.ItemDecoration? = null

    @ViewPager2.Orientation
    protected open var orientation = ViewPager2.ORIENTATION_HORIZONTAL
    protected open val pageCallbacks: MutableList<ViewPager2.OnPageChangeCallback> by lazy {
        mutableListOf()
    }
    val viewPager: ViewPager2
        get() = let {
            checkAttach()
            viewIF.binding.viewPager
        }

    // 分页数据发生变化时更新预加载页面数
    private val adapterDataObserver: RecyclerView.AdapterDataObserver by lazy {
        object: RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                updateOffscreenLimit()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                updateOffscreenLimit()
            }
        }
    }

    override fun onViewAttached(view: View) {
        initViewPager()
    }


    /**
     * Set the divider
     * @param orientation Int
     * @return ViewPager2VModel<V>
     */
    fun orientation(@ViewPager2.Orientation orientation: Int) = apply {
        checkNotAttach("set orientation")
        this.orientation = orientation
    }

    /**
     * Set the transition animation
     * @param transformer PageTransformer
     * @return ViewPager2VModel<V>
     */
    fun pageTransformer(transformer: ViewPager2.PageTransformer) = apply {
        checkNotAttach("set pageTransformer")
        this.pageTransformer = transformer
    }

    /**
     * Set the default page index
     * @param position Int
     */
    fun defaultPosition(position: Int) = apply {
        checkNotAttach("set defaultPosition")
        this.defaultPosition = position
    }


    /**
     * Sets the number of page load caches
     * @param limit Int
     */
    fun offScreenPageLimit(limit: Int) = apply {
        checkNotAttach("set offScreenPageLimit")
        this.offscreenPageLimit = limit
    }


    /**
     * Whether page sliding is supported
     * @param isScroll Boolean
     * @return ViewPager2VModel<V>
     */
    fun supportScroll(isScroll: Boolean) = apply {
        checkNotAttach("set supportScroll")
        this.isUserInputEnabled = isScroll
    }


    /**
     * ViewPager init Config
     */
    private fun initViewPager() {
        registerPageChangeCallback()
        itemDecoration?.let {
            viewPager.removeItemDecoration(it)
            viewPager.addItemDecoration(it)
        }
        viewPager.isUserInputEnabled = isUserInputEnabled
        viewPager.isSaveEnabled = false
        //替换内部LayoutManager，修复使用DiffUtil刷新，底层抛出数据越界问题
//        viewPager.setLayoutManager(LinearWrapperLayoutManager(context))
        viewPager.setOverscrollMode(ViewPager2.OVER_SCROLL_NEVER)
        viewPager.orientation = orientation
        viewPager.setPageTransformer(pageTransformer)
        updateOffscreenLimit()
        viewPager.adapter = adapter
        adapter.registerAdapterDataObserver(adapterDataObserver)
        setDefaultPage()
    }

    private fun updateOffscreenLimit() {
        viewPager.offscreenPageLimit = if (adapter.itemCount > 1) {
            adapter.itemCount
        } else {
            offscreenPageLimit
        }
    }

    /**
     * Set the default display page
     */
    protected open fun setDefaultPage() {
        if (defaultPosition == 0) {
            return
        }
        if (defaultPosition > adapter.currentList.size - 1) {
            return
        }
        viewPager.post {
            setCurrentItem(defaultPosition, false)
        }
    }


    /**
     * register All OnPageChangeCallback
     */
    private fun registerPageChangeCallback() {
        pageCallbacks.forEach {
            viewPager.registerOnPageChangeCallback(it)
        }
    }

    /**
     * unregister All OnPageChangeCallback
     */
    private fun unregisterPageChangeCallback() {
        pageCallbacks.forEach {
            viewPager.unregisterOnPageChangeCallback(it)
        }
        adapter.unregisterAdapterDataObserver(adapterDataObserver)
    }

    /**
     * add OnPageChangeCallback
     * @param callback OnPageChangeCallback
     */
    fun addPageChangeCallback(callback: ViewPager2.OnPageChangeCallback) = apply {
        if (!pageCallbacks.contains(callback)) {
            pageCallbacks.add(callback)
        }
        if (isAttach()) {
            viewPager.registerOnPageChangeCallback(callback)
        }
    }

    /**
     * remove OnPageChangeCallback
     * @param callback OnPageChangeCallback
     */
    fun removePageChangeCallback(callback: ViewPager2.OnPageChangeCallback) = apply {
        if (pageCallbacks.contains(callback)) {
            pageCallbacks.remove(callback)
        }
        if (isAttach()) {
            viewPager.unregisterOnPageChangeCallback(callback)
        }
    }


    override fun onRelease() {
        super.onRelease()
        unregisterPageChangeCallback()
    }

    /**
     * Switch the page
     * @param page Int
     */
    open fun setCurrentItem(page: Int) {
        setCurrentItem(page, false)
    }

    /**
     * Switch the page
     * @param page Int
     * @param smoothScroll Boolean
     */
    open fun setCurrentItem(page: Int, smoothScroll: Boolean) {
        if (!isAttach()) {
            return
        }
        viewPager.setCurrentItem(page, smoothScroll)
    }

    /**
     * current Item
     * @return Int
     */
    fun currentItem(): Int = let {
        if (!isAttach()) {
            -1
        } else {
            viewPager.currentItem
        }
    }


    @Deprecated(message = "This attribute is not used", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    override fun actionClick(): View.OnClickListener {
        return View.OnClickListener { }
    }

    @Deprecated(message = "This attribute is not used", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    override fun click(func: (View) -> Unit) {
    }

    @Deprecated(message = "This attribute is not used", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    override fun enabled(value: Boolean) {
    }

    @Deprecated(message = "This attribute is not used", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    override fun clickable(value: Boolean) {
    }


    override fun itemEquals(t: MutableList<ViewModel<*>>): Boolean {
        return this.hashCode() == t.hashCode() && CollectionUtils.isEqualCollection(t, adapter.currentList)
    }

    override fun getItem(): MutableList<ViewModel<*>> {
        return adapter.currentList
    }

}
