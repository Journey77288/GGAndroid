package io.commponent.indicator

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/23
 *   @desc   : Indicator is an abstract class
 * </pre>
 */
abstract class BaseIndicator : Indicator {
    override var heightPx: Int = 20
    override var widthPx: Int = 20
    private val viewCacheArray: SparseArray<View> = SparseArray()

    /**
     * create Recycler ViewHolder View
     * @param parent ViewGroup
     * @param position Int
     * @return View
     */
    override fun createHolderView(parent: ViewGroup, position: Int): View {
        val indicator = createView(parent, position)
        viewCacheArray.put(position, indicator)
        return indicator
    }


    /**
     * get Indicator View
     * @param position Int
     * @return View?
     */
    override fun getView(position: Int): View? {
        return viewCacheArray.get(position)
    }

    /**
     * Update the selection indicator status
     * @param position Int
     */
    override fun updateSelectedViewState(position: Int) {
        resetAllViewState()
        getView(position)?.isSelected = true
    }


    /**
     * reset All Indicator View State
     */
    override fun resetAllViewState() {
        for (index in 0 until viewCacheArray.size()) {
            try {
                viewCacheArray.valueAt(index).isSelected = false
            } catch (e: Exception) {
                e.printStackTrace()
                throw Throwable(e)
            }
        }
    }



}


