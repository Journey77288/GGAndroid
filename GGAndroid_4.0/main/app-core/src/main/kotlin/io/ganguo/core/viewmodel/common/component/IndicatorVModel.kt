package io.ganguo.core.viewmodel.common.component

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.commponent.indicator.Indicator
import io.commponent.indicator.IndicatorLayout
import io.ganguo.core.R
import io.ganguo.core.databinding.ComponentIndicatorBinding
import io.ganguo.core.viewmodel.BaseViewModel
import io.ganguo.mvvm.viewinterface.ViewInterface

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/23
 *   @desc   : Indicator
 * </pre>
 */
class IndicatorVModel : BaseViewModel<ViewInterface<ComponentIndicatorBinding>>() {
    override val layoutId: Int = R.layout.component_indicator


    @RecyclerView.Orientation
    private var orientation: Int = RecyclerView.HORIZONTAL
    private var indicatorSpace: Int = 0
    private var indicator: Indicator? = null
    private var defaultPosition: Int = 0

    private val indicatorLayout: IndicatorLayout
        get() {
            return viewIF.binding.indicator
        }

    override fun onViewAttached(view: View) {
        indicatorLayout.setOrientation(orientation)
        indicatorLayout.setIndicatorSpace(indicatorSpace)
        checkNotNull(indicator) {
            "Indicators must be set"
        }
        indicatorLayout.setIndicator(indicator!!, defaultPosition)

    }

    /**
     * Indicator style, must be configured
     * @param indicator Indicator
     * @return IndicatorVModel
     */
    fun indicator(indicator: Indicator): IndicatorVModel = apply {
        this.indicator = indicator
    }

    /**
     * Indicator display direction
     * @param orientation Int
     * @return IndicatorVModel
     */
    fun orientation(@RecyclerView.Orientation orientation: Int): IndicatorVModel = apply {
        this.orientation = orientation
    }

    /**
     * Indicator spacing
     * @param indicatorSpace Int
     * @return IndicatorVModel
     */
    fun indicatorSpace(indicatorSpace: Int): IndicatorVModel = apply {
        this.indicatorSpace = indicatorSpace
    }


    /**
     * Default selected position
     * @param defaultPosition Int
     * @return IndicatorVModel
     */
    fun defaultPosition(defaultPosition: Int): IndicatorVModel = apply {
        this.defaultPosition = defaultPosition
    }


    /**
     * Select the indicator for position
     * @param position Int
     */
    fun selectedIndicator(position: Int) {
        if (!isAttach()) {
            return
        }
        indicatorLayout.selectedIndicator(position)
    }


}
