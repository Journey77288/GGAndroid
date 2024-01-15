package io.component.banner

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.IntDef
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import io.component.banner.listener.BannerPageChangeListener
import io.component.banner.listener.ViewPagerPageChangeListener
import io.ganguo.utils.view.setLayoutManager
import java.util.*
import kotlin.math.abs

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/12/26
 *   @desc   : Modify the adaptation framework based on banner library
 * </pre>
 * @see [banner](https://github.com/youth5201314/banner)
 */
open class Banner @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {
    internal var pageChangeListener: BannerPageChangeListener? = null
    private var shufflingScrollMillisecond = SHUFFLING_SCROLL_MILLI_SECOND.toLong()
    internal var shufflingIntervalMillisecond = SHUFFLING_INTERVAL_MILLI_SECOND.toLong()
    internal var isAutoPlay = IS_AUTO_PLAY
    private var adapter: RecyclerView.Adapter<*>? = null
    private var startPosition = 2
    private var bannerRadius = 0f
    private var isIntercept = true
    var isLoop = IS_LOOP

    private var viewPager2: ViewPager2? = null
    internal var loopTask: BannerAutoPlayTask? = null
    private var positionDelegate: BannerPositionDelegate? = null
    private var pageChangeCallback: ViewPagerPageChangeListener? = null
    private var bannerLayoutManager: BannerSpeedLayoutManger? = null
    private var touchSlop = 0
    private var startX = 0f
    private var startY = 0f
    private var isViewPager2Drag = false
    private var roundPaint: Paint? = null
    private var imagePaint: Paint? = null
    val currentItem: Int
        get() = viewPager2!!.currentItem
    val itemCount: Int
        get() = adapter?.itemCount ?: 0
    val realCount: Int
        get() = positionDelegate!!.getRealCount()

    private val lifecycleObserver: BannerLifecycleObserver by lazy {
        BannerLifecycleObserver(this)
    }
    private var dataObserver: RecyclerView.AdapterDataObserver? = null


    init {
        init(context)
        initTypedArray(context, attrs)
    }

    /**
     * Banner initialize
     * @param context Context
     */
    private fun init(context: Context) {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop / 2
        pageChangeCallback = ViewPagerPageChangeListener(this)
        loopTask = BannerAutoPlayTask(this)
        viewPager2 = ViewPager2(context)
        viewPager2!!.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        viewPager2!!.offscreenPageLimit = 1
        viewPager2!!.registerOnPageChangeCallback(pageChangeCallback!!)
        bannerLayoutManager = BannerSpeedLayoutManger(context, shufflingScrollMillisecond, viewPager2!!.orientation)
        viewPager2!!.setLayoutManager(bannerLayoutManager!!)
        addView(viewPager2)
        roundPaint = Paint()
        roundPaint!!.color = Color.WHITE
        roundPaint!!.isAntiAlias = true
        roundPaint!!.style = Paint.Style.FILL
        roundPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        imagePaint = Paint()
        imagePaint!!.xfermode = null
    }


    /**
     * AttributeSet initialize
     * @param context Context
     * @param attrs AttributeSet?
     */
    private fun initTypedArray(context: Context, attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }
        val a = context.obtainStyledAttributes(attrs, R.styleable.Banner)
        bannerRadius = a.getDimensionPixelSize(R.styleable.Banner_banner_radius, 0).toFloat()
        shufflingIntervalMillisecond = a.getInt(R.styleable.Banner_banner_loop_time, SHUFFLING_INTERVAL_MILLI_SECOND).toLong()
        isAutoPlay = a.getBoolean(R.styleable.Banner_banner_auto_loop, IS_AUTO_PLAY)
        isLoop = a.getBoolean(R.styleable.Banner_banner_infinite_loop, IS_LOOP)
        val orientation = a.getInt(R.styleable.Banner_banner_orientation, HORIZONTAL)
        setOrientation(orientation)
        setBannerRound(bannerRadius)
        a.recycle()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (!viewPager2!!.isUserInputEnabled) {
            return super.dispatchTouchEvent(ev)
        }
        val action = ev.actionMasked
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            start()
        } else if (action == MotionEvent.ACTION_DOWN) {
            stop()
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (!viewPager2!!.isUserInputEnabled || !isIntercept) {
            return super.onInterceptTouchEvent(event)
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = event.x
                val endY = event.y
                val distanceX = abs(endX - startX)
                val distanceY = abs(endY - startY)
                isViewPager2Drag = if (viewPager2!!.orientation == HORIZONTAL) {
                    distanceX > touchSlop && distanceX > distanceY
                } else {
                    distanceY > touchSlop && distanceY > distanceX
                }
                parent.requestDisallowInterceptTouchEvent(isViewPager2Drag)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(false)
        }
        return super.onInterceptTouchEvent(event)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // 设置自动滑动的位置，避免自动滑动时离屏时卡在中间的情况
        setCurrentItem(currentItem, false)
        stop()
    }

    /**
     * Sets the item margin for Padding
     * @param itemPadding Int
     */
    private fun setRecyclerViewPadding(itemPadding: Int) {
        setRecyclerViewPadding(itemPadding, itemPadding)
    }

    /**
     * Sets the item margin for Padding
     * @param leftItemPadding Int
     * @param rightItemPadding Int
     */
    private fun setRecyclerViewPadding(leftItemPadding: Int, rightItemPadding: Int) {
        val recyclerView = viewPager2!!.getChildAt(0) as RecyclerView
        if (viewPager2!!.orientation == ViewPager2.ORIENTATION_VERTICAL) {
            recyclerView.setPadding(0, leftItemPadding, 0, rightItemPadding)
        } else {
            recyclerView.setPadding(leftItemPadding, 0, rightItemPadding, 0)
        }
        recyclerView.clipToPadding = false
    }

    /**
     * Set whether to loop
     * @param loop Boolean
     * @return Banner
     */
    fun setLoop(loop: Boolean): Banner = apply {
        isLoop = loop
        if (!isLoop) {
            setAutoPlay(false)
        }
        startPosition = (if (isLoop) 2 else 0)
    }

    /**
     * Whether to intercept gesture events
     *
     * @param intercept
     * @return
     */
    fun setIntercept(intercept: Boolean): Banner = apply {
        isIntercept = intercept
    }

    /**
     * Settings scroll to the specified page, preferably after adding data, otherwise it doesn't make sense
     *
     * @param position
     * @return
     */
    fun setCurrentItem(position: Int): Banner = apply {
        setCurrentItem(position, true)
    }

    /**
     * Settings scroll to the specified page, preferably after adding data, otherwise it doesn't make sense
     *
     * @param position
     * @param smoothScroll Sliding transition or not
     * @return
     */
    fun setCurrentItem(position: Int, smoothScroll: Boolean): Banner = apply {
        viewPager2!!.setCurrentItem(position, smoothScroll)
    }


    /**
     * Whether manual sliding is prohibited
     * @param enabled Boolean
     * @return Banner
     */
    fun setUserInputEnabled(enabled: Boolean): Banner = apply {
        viewPager2!!.isUserInputEnabled = enabled
    }

    /**
     * Set the page switching transition animation converter to support only one
     * @param transformer PageTransformer?
     * @return Banner
     */
    fun setPageTransformer(transformer: ViewPager2.PageTransformer?): Banner = apply {
        viewPager2!!.setPageTransformer(transformer)
    }

    /**
     * Add a page divider
     * @param decor ItemDecoration?
     * @return Banner
     */
    fun addItemDecoration(decor: RecyclerView.ItemDecoration?): Banner = apply {
        if (decor != null) {
            viewPager2!!.addItemDecoration(decor)
        }
    }

    fun addItemDecoration(decor: RecyclerView.ItemDecoration?, index: Int): Banner = apply {
        if (decor != null) {
            viewPager2!!.addItemDecoration(decor, index)
        }
    }

    fun addItemDecoration(decors: HashMap<Int, RecyclerView.ItemDecoration>): Banner = apply {
        decors.forEach {
            addItemDecoration(it.value, it.key)
        }
    }

    /**
     * Whether automatic wheel seeding is allowed
     * @param isAutoLoop Boolean
     * @return Banner
     */
    fun setAutoPlay(isAutoLoop: Boolean): Banner = apply {
        isAutoPlay = isAutoLoop
    }

    /**
     * Set the length of the page slide transition
     * @param millisecond Long
     * @return Banner
     */
    fun setShufflingIntervalMillisecond(millisecond: Long): Banner = apply {
        shufflingIntervalMillisecond = millisecond
    }

    /**
     * Set the duration of the wheel seeding interval
     * @param millisecond Long
     * @return Banner
     */
    fun setShufflingScrollMillisecond(millisecond: Long): Banner = apply {
        shufflingScrollMillisecond = millisecond
    }

    /**
     * start playing
     */
    fun start(): Banner = apply {
        if (isAutoPlay) {
            stop()
            postDelayed(loopTask, shufflingIntervalMillisecond)
        }
    }

    /**
     * Stop playing
     */
    fun stop(): Banner = apply {
        if (isAutoPlay) {
            removeCallbacks(loopTask)
        }
    }

    /**
     * Release resources
     */
    fun destroy() {
        if (viewPager2 != null && pageChangeCallback != null) {
            viewPager2!!.unregisterOnPageChangeCallback(pageChangeCallback!!)
        }
        if (this.adapter != null && this.adapter!!.hasObservers() && dataObserver != null) {
            this.adapter?.unregisterAdapterDataObserver(dataObserver!!)
        }
        stop()
    }

    /**
     * Set the Banner data adapter
     * @param adapter Adapter<*>?  Adapter must implement BannerPositionDelegate
     * @return Banner
     */
    @Synchronized
    open fun setAdapter(adapter: RecyclerView.Adapter<*>): Banner = apply {
        check(adapter is BannerPositionDelegate) {
            "Adapter must implement BannerPositionDelegate"
        }
        this.positionDelegate = adapter
        if (!isLoop) {
            positionDelegate!!.setIncreaseCount(0)
        }
        this.adapter = adapter
        dataObserver = BannerAdapterDataObserver(this)
        this.adapter?.registerAdapterDataObserver(dataObserver!!)
        viewPager2!!.setLayoutManager(bannerLayoutManager!!)
        removeCallbacks(loopTask)
        viewPager2!!.adapter = adapter
        setCurrentItem(startPosition, false)
    }

    /**
     * Set the direction of the Banner wheel
     *
     * @param orientation [Orientation]
     */
    open fun setOrientation(@Orientation orientation: Int): Banner = apply {
        viewPager2!!.orientation = orientation
    }

    /**
     * Sets the minimum sliding distance
     * @param touchSlop Int
     * @return Banner
     */
    open fun setTouchSlop(touchSlop: Int): Banner = apply {
        if (touchSlop > 0) {
            this.touchSlop = touchSlop
        }
    }


    /**
     * The Banner page switches callbacks
     * @param pageListener BannerPageChangeListener?
     * @return Banner
     */
    open fun setBannerChangeListener(pageListener: BannerPageChangeListener?): Banner = apply {
        pageChangeListener = pageListener
    }


    /**
     * Draw the rounded corners
     * @param canvas Canvas
     */
    override fun dispatchDraw(canvas: Canvas) {
        if (bannerRadius > 0) {
            var path = Path()
            var rectF = RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
            path.addRoundRect(rectF, bannerRadius, bannerRadius, Path.Direction.CW)
            canvas.clipPath(path)
        }
        super.dispatchDraw(canvas)
    }


    /**
     * Set the rounded corners for Banner only. For Item, handle the rounded corners in Item
     * @param radius Float
     * @return Banner
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    open fun setBannerRound(radius: Float): Banner = apply {
        this.bannerRadius = radius
        postInvalidate()
    }


    /**
     * Add the gallery effect to the Banner
     *
     * @param leftItemWidth  Show the width on the left，The unit pixel
     * @param rightItemWidth Show the width on the right，The unit pixel
     * @param pageMargin     Page space，The unit pixel
     * @param scale     The interval 0- 1,1 means no scaling
     */
    open fun setBannerGalleryEffect(@Px leftItemWidth: Int, @Px rightItemWidth: Int, @Px pageMargin: Int): Banner = apply {
        if (leftItemWidth < 0 || rightItemWidth < 0) {
            setRecyclerViewPadding(0, 0)
            return@apply
        }
        val leftPadding = if (leftItemWidth > 0) {
            leftItemWidth + pageMargin
        } else {
            0
        }
        val rightPadding = if (rightItemWidth > 0) {
            rightItemWidth + pageMargin
        } else {
            0
        }
        setRecyclerViewPadding(leftPadding, rightPadding)
    }


    /**
     * Add the meizu effect to the banner
     *
     * @param amboWidth Item left and right margins，The unit pixel
     * @param scale     The interval 0- 1,1 means no scaling
     */
    open fun setBannerGalleryMZ(@Px meiZuBmboWidth: Int): Banner = apply {
        if (meiZuBmboWidth > 0) {
            setRecyclerViewPadding(meiZuBmboWidth)
        }
    }

    /**
     * binding lifecycle to Banner
     * @param owner LifecycleOwner?
     * @return Banner
     */
    fun bindLifecycle(owner: LifecycleOwner?): Banner = apply {
        owner?.lifecycle?.addObserver(BannerLifecycleAdapter(lifecycleObserver))
    }

    companion object {
        const val INVALID_VALUE = -1
        const val HORIZONTAL = 0
        const val VERTICAL = 1
        const val IS_AUTO_PLAY = true
        const val IS_LOOP = true
        const val SHUFFLING_INTERVAL_MILLI_SECOND = 3000
        const val SHUFFLING_SCROLL_MILLI_SECOND = 1000
    }

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @IntDef(HORIZONTAL, VERTICAL)
    annotation class Orientation

}
