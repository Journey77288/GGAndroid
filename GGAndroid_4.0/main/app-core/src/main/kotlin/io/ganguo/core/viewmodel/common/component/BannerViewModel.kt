package io.ganguo.core.viewmodel.common.component

import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.CollectionUtils
import io.component.banner.Banner
import io.component.banner.listener.BannerPageChangeListener
import io.component.banner.transformer.ScaleInTransformer
import io.ganguo.core.R
import io.ganguo.core.adapter.BannerViewModelAdapter
import io.ganguo.core.databinding.ComponentBannerBinding
import io.ganguo.core.viewmodel.BaseViewModel
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.support.recyclerview.adapter.diffuitl.IDiffComparator

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/05
 *   @desc   : Banner
 * </pre>
 */
class BannerViewModel(private val parent: ViewModel<*>, private val adapter: BannerViewModelAdapter = BannerViewModelAdapter(parent.context, parent)) :
        BaseViewModel<ViewInterface<ComponentBannerBinding>>(), IDiffComparator<MutableList<ViewModel<*>>> {
    override val layoutId: Int = R.layout.component_banner
    val banner: Banner
        get() = let {
            checkAttach()
            viewIF.binding.root as Banner
        }
    private var shufflingScrollMillisecond: Long = 800
    private var shufflingIntervalMillisecond: Long = 3000
    private var isLoop: Boolean = true
    private var touchSlop: Int = 0
    private var isAutoPlay: Boolean = true
    private var itemDecoration: RecyclerView.ItemDecoration? = null
    private var itemDecorations = HashMap<Int, RecyclerView.ItemDecoration>()
    private var isIntercept: Boolean = false
    private var bannerChangeListener: BannerPageChangeListener? = null
    private var isUserInputEnabled: Boolean = true
    private var bannerRadius: Float = 0f
    private var pageTransformers: MutableList<ViewPager2.PageTransformer> = mutableListOf()

    @Px
    private var galleryLeftWidth: Int = -1

    @Px
    private var galleryRightWidth: Int = -1

    @Px
    private var galleryPageMargin: Int = -1

    @Px
    private var meiZuAmboWidth: Int = -1

    @RecyclerView.Orientation
    private var orientation: Int = RecyclerView.HORIZONTAL

    override fun onViewAttached(view: View) {
        banner.bindLifecycle(lifecycleOwner)
        banner.setShufflingScrollMillisecond(shufflingScrollMillisecond)
        banner.setShufflingIntervalMillisecond(shufflingIntervalMillisecond)
        banner.setAutoPlay(isAutoPlay)
        banner.setLoop(isLoop)
        banner.setTouchSlop(touchSlop)
        banner.addItemDecoration(itemDecoration)
        banner.addItemDecoration(itemDecorations)
        banner.setUserInputEnabled(isUserInputEnabled)
        banner.setPageTransformer(listToTransformer())
        banner.setBannerChangeListener(bannerChangeListener)
        banner.setAdapter(adapter)
        banner.setIntercept(isIntercept)
        banner.setBannerRound(bannerRadius)
        banner.setBannerGalleryEffect(galleryLeftWidth, galleryRightWidth, galleryPageMargin)
        banner.setBannerGalleryMZ(meiZuAmboWidth)
        banner.start()
    }


    /**
     * pageTransformers to ViewPager2.PageTransformer
     * @return ViewPager2.PageTransformer
     */
    private fun listToTransformer(): ViewPager2.PageTransformer? = let {
        if (pageTransformers.isEmpty()) {
            return@let null
        }
        CompositePageTransformer()
                .apply {
                    pageTransformers.forEach {
                        addTransformer(it)
                    }
                }
    }


    /**
     * set Banner orientation
     * @param orientation Int
     * @return BannerViewModel
     */
    fun orientation(@RecyclerView.Orientation orientation: Int): BannerViewModel = apply {
        this.orientation = orientation
    }

    /**
     * set BannerViewPager Transition converter
     * @param transformer PageTransformer?
     * @return BannerViewModel
     */
    fun pageTransformer(transformer: ViewPager2.PageTransformer): BannerViewModel = apply {
        this.pageTransformers.add(transformer)
    }

    /**
     * Auto play wheel play interval，In milliseconds
     * @param periodMillisecond Long
     * @return BannerViewModel
     */
    fun shufflingIntervalMillisecond(intervalMillisecond: Long): BannerViewModel = apply {
        this.shufflingIntervalMillisecond = intervalMillisecond
    }

    /**
     * Page switch slide transition time，In milliseconds
     * @param scrollMillisecond Long
     * @return BannerViewModel
     */
    fun shufflingScrollMillisecond(scrollMillisecond: Long): BannerViewModel = apply {
        this.shufflingScrollMillisecond = scrollMillisecond
    }

    /**
     * page loop scroll
     * @return BannerViewModel
     */
    fun loop(loop: Boolean = true): BannerViewModel = apply {
        isLoop = loop
    }


    /**
     * Sets the minimum sliding distance
     * @param touchSlop Int
     * @return Banner
     */
    fun touchSlop(touchSlop: Int = 0): BannerViewModel = apply {
        this.touchSlop = touchSlop
    }

    /**
     * Whether automatic wheel seeding is allowed
     * @param isAutoLoop Boolean
     * @return Banner
     */
    fun autoPlay(autoPlay: Boolean): BannerViewModel = apply {
        this.isAutoPlay = autoPlay
    }

    /**
     * add ItemDecoration
     */
    fun addItemDecoration(decor: RecyclerView.ItemDecoration): BannerViewModel = apply {
        this.itemDecoration = decor
    }

    fun addItemDecoration(decor: RecyclerView.ItemDecoration, index: Int): BannerViewModel = apply {
        this.itemDecorations.put(index, decor)
    }

    /**
     * Whether to intercept gesture events
     *
     * @param intercept
     * @return
     */
    fun setIntercept(intercept: Boolean): BannerViewModel = apply {
        this.isIntercept = intercept
    }


    /**
     * The Banner page switches callbacks
     * @param pageListener BannerPageChangeListener?
     * @return Banner
     */
    fun setBannerChangeListener(pageListener: BannerPageChangeListener?): BannerViewModel = apply {
        this.bannerChangeListener = pageListener
    }

    /**
     * Set Banner to display rounded corners
     * @param radius Float
     * @return BannerViewModel
     */
    fun setBannerRound(radius: Float): BannerViewModel = apply {
        this.bannerRadius = radius
    }


    /**
     * Whether manual sliding is prohibited
     * @param enabled Boolean
     * @return Banner
     */
    fun setUserInputEnabled(enabled: Boolean): BannerViewModel = apply {
        this.isUserInputEnabled = enabled
    }


    /**
     * Add the gallery effect to the Banner
     *
     * @param galleryLeftWidth  Show the width on the left，The unit pixel
     * @param galleryRightWidth Show the width on the right，The unit pixel
     * @param galleryPageMargin     Page space，The unit pixel
     * @param scale     The interval 0- 1,1 means no scaling
     */
    fun toGalleryEffect(@Px galleryLeftWidth: Int, @Px galleryRightWidth: Int, @Px galleryPageMargin: Int, scale: Float = 0.88f): BannerViewModel = apply {
        check(galleryPageMargin >= 0) {
            "pageMargin must be greater than or equal to 0"
        }
        if (galleryPageMargin > 0) {
            this.pageTransformers.add(MarginPageTransformer(galleryPageMargin))
        }
        if (scale > 0) {
            this.pageTransformers.add(ScaleInTransformer(scale))
        }
        this.galleryLeftWidth = galleryLeftWidth
        this.galleryRightWidth = galleryRightWidth
        this.galleryPageMargin = galleryPageMargin
    }


    /**
     * Add the gallery effect to the Banner
     *
     * @param meizuBmboWidth  Width is exposed on both sides of the page
     * @param scale     The interval 0- 1,1 means no scaling
     */
    fun toMeiZuEffect(@Px meiZuBmboWidth: Int, scale: Float = 0.88f): BannerViewModel = apply {
        check(meiZuBmboWidth >= 0) {
            "meizuBmboWidth must be greater than 0"
        }
        check(scale >= 0) {
            "meizuBmboWidth must be greater than 0"
        }
        this.meiZuAmboWidth = meiZuBmboWidth
        this.pageTransformers.clear()
        if (scale > 0) {
            this.pageTransformers.add(ScaleInTransformer(scale))
        }
    }

    fun add(viewModel: ViewModel<*>) {
        adapter.add(viewModel)
    }

    fun addAll(@NonNull viewModels: List<ViewModel<*>>) {
        adapter.addAll(viewModels)
    }

    fun clear() {
        adapter.clear()
    }

    override fun itemEquals(t: MutableList<ViewModel<*>>): Boolean {
        return this.hashCode() == t.hashCode() && CollectionUtils.isEqualCollection(t, adapter.currentList)
    }

    override fun getItem(): MutableList<ViewModel<*>> {
        return adapter.currentList
    }

}
