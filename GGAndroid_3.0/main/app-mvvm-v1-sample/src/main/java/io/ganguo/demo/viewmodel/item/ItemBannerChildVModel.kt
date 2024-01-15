package io.ganguo.demo.viewmodel.item

import androidx.databinding.ObservableField
import android.view.View

import io.ganguo.demo.R
import io.ganguo.demo.databinding.ItemBannerChildBinding
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.viewinterface.AdapterInterface

/**
 * Created by leo on 2017/4/27.
 * 广告轮播 - ItemViewModel
 */
class ItemBannerChildVModel : BaseViewModel<io.ganguo.viewmodel.core.viewinterface.AdapterInterface<ItemBannerChildBinding>>() {
    val imageUrl = ObservableField<String>()
    override val layoutId: Int by lazy { R.layout.item_banner_child }
    override fun onViewAttached(view: View) {

    }

    fun setImageUrl(imageUrl: String): ItemBannerChildVModel {
        this.imageUrl.set(imageUrl)
        return this
    }

}
