package io.ganguo.sample.viewmodel.support.cache

import android.view.View
import androidx.databinding.ObservableField
import io.ganguo.mvvm.viewinterface.AdapterInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.sample.R
import io.ganguo.sample.databinding.ItemFooterButtonBinding

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/08
 *     desc   : Footer with two horizontal button
 * </pre>
 */
class ItemFooterButtonVModel : ViewModel<AdapterInterface<ItemFooterButtonBinding>>() {
    override val layoutId: Int = R.layout.item_footer_button
    val leftTitle = ObservableField<String>()
    val rightTitle = ObservableField<String>()
    var leftCallback: (() -> Unit)? = null
    var rightCallback: (() -> Unit)? = null

    override fun onViewAttached(view: View) {

    }

    /**
     * Click left button
     *
     * @param view
     */
    fun actionClickLeftButton(view: View) {
        leftCallback?.invoke()
    }

    /**
     * Click right button
     *
     * @param view
     */
    fun actionClickRightButton(view: View) {
        rightCallback?.invoke()
    }
}