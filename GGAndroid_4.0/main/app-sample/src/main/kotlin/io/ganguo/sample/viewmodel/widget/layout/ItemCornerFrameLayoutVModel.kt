package io.ganguo.sample.viewmodel.widget.layout

import android.view.View
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.sample.R
import io.ganguo.sample.databinding.ItemCorerFrameLayoutBinding

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/13
 *   @desc   : CornerFrameLayout Sample
 * </pre>
 */
class ItemCornerFrameLayoutVModel : ViewModel<ViewInterface<ItemCorerFrameLayoutBinding>>() {
    override val layoutId: Int = R.layout.item_corer_frame_layout

    override fun onViewAttached(view: View) {
    }

}
