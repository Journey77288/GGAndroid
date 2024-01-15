package io.commponent.indicator

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/23
 *   @desc   : Custom indicator
 * </pre>
 */
class IndicatorLayout : RecyclerView {
    @Orientation
    private var orientation: Int = HORIZONTAL
    private var indicatorSpace: Int = 0
    private var indicator: Indicator? = null
    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context, this@IndicatorLayout.orientation, false)
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initTypedArray(context, attrs)
        initIndicatorView()
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
        val obtain = context.obtainStyledAttributes(attrs, R.styleable.Indicator)
        orientation = obtain.getInt(R.styleable.Indicator_android_orientation, HORIZONTAL)
        indicatorSpace = obtain.getDimensionPixelOffset(R.styleable.Indicator_indicator_space, 0)
        obtain.recycle()
    }


    /**
     * config IndicatorLayout
     */
    private fun initIndicatorView() {
        setOrientation(orientation)
        setIndicatorSpace(indicatorSpace)
        setLayoutManager(layoutManager)
    }


    /**
     * Set indicator direction
     * @param orientation Int
     */
    fun setOrientation(@Orientation orientation: Int) {
        this.orientation = orientation
        layoutManager.orientation = orientation
    }


    /**
     * set Indicator
     * @param indicator Indicator
     */
    fun setIndicator(indicator: Indicator, defaultPosition: Int = 0) {
        this.indicator = indicator
        adapter = IndicatorViewAdapter(indicator)
        adapter!!.notifyDataSetChanged()
        post { selectedIndicator(defaultPosition) }
    }

    /**
     * set Indicator Item Space
     * @param indicatorSpace Int
     */
    fun setIndicatorSpace(indicatorSpace: Int) {
        if (indicatorSpace != 0) {
            clearItemDecoration()
            addItemDecoration(IndicatorDecoration(orientation, indicatorSpace))
        }
    }


    /**
     * Select the indicator for position
     * @param position Int
     */
    fun selectedIndicator(position: Int) {
        indicator?.updateSelectedViewState(position)
    }


    /**
     * clear Recycler ItemDecoration
     */
    private fun clearItemDecoration() {
        for (i in 0 until itemDecorationCount) {
            removeItemDecorationAt(i)
        }
    }

}
