package io.ganguo.sample.viewmodel.component

import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import io.ganguo.mvvm.viewinterface.AdapterInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.sample.R
import io.ganguo.sample.databinding.ItemCityBinding
import io.ganguo.sticky_header.StickyHeaderConfig
import io.ganguo.sticky_header.StickyHeaderHandler
import io.support.recyclerview.adapter.diffuitl.IDiffComparator

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/19
 *     desc   : City ViewModel
 * </pre>
 */
class ItemCityVModel(val name: String) : ViewModel<AdapterInterface<ItemCityBinding>>(),
        IDiffComparator<String>, StickyHeaderHandler {
    override val layoutId: Int = R.layout.item_city

    override fun onViewAttached(view: View) {

    }

    override fun itemEquals(t: String): Boolean {
        return name == t
    }

    override fun getItem(): String {
        return name
    }

    override val stickyHeaderConfig: StickyHeaderConfig by lazy {
        val letter = StickyHeaderHandler.subFirstPinYin(name)
        val height = getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_27)
        val bgColor = getColor(io.ganguo.resources.R.color.graywhite)
        val textColor = Color.BLACK
        val textSize = getDimension(io.ganguo.resources.R.dimen.font_14)
        val left = getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_16)
        StickyHeaderConfig(letter, height, bgColor, textColor, textSize, left, Typeface.DEFAULT_BOLD)
    }
}