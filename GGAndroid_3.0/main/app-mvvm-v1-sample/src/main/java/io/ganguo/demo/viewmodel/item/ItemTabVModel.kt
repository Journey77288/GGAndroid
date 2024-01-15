package io.ganguo.demo.viewmodel.item

import androidx.annotation.DrawableRes
import android.view.View

import io.ganguo.demo.R
import io.ganguo.demo.databinding.ItemTabIconViewBinding
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface

/**
 * Created by leo on 16/7/11.
 * item tab
 */
class ItemTabVModel(@field:DrawableRes
                    var icon: Int, var title: String?) : BaseViewModel<ActivityInterface<ItemTabIconViewBinding>>() {

    override fun onViewAttached(view: View) {
    }

    override val layoutId: Int by lazy { R.layout.item_tab_icon_view }
}
