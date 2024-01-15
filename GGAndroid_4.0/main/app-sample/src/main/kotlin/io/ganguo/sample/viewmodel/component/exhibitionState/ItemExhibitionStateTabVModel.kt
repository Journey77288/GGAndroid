package io.ganguo.sample.viewmodel.component.exhibitionState

import android.view.View
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.sample.R
import io.ganguo.sample.databinding.ItemExhibitionStateTabBinding

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/19
 *     desc   : Exhibition State Tab ViewModel
 * </pre>
 */
class ItemExhibitionStateTabVModel(val title: String) : ViewModel<ViewInterface<ItemExhibitionStateTabBinding>>() {
    override val layoutId: Int = R.layout.item_exhibition_state_tab

    override fun onViewAttached(view: View) {

    }
}