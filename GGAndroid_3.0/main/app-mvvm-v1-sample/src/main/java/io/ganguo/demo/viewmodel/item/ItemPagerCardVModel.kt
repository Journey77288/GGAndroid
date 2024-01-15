package io.ganguo.demo.viewmodel.item

import androidx.databinding.ObservableField
import android.view.View

import io.ganguo.demo.R
import io.ganguo.demo.databinding.ItemPagerCardBinding
import io.ganguo.demo.entity.ImageEntity
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.viewinterface.ViewInterface

/**
 * Created by leo on 16/7/31.
 * Item Pager-card
 */
class ItemPagerCardVModel(private val image: ImageEntity) : BaseViewModel<ViewInterface<ItemPagerCardBinding>>() {
    val name = ObservableField<String>()
    val imageUrl = ObservableField<String>()

    init {
        this.name.set(image.name)
        this.imageUrl.set(image.image)
    }

    override fun onViewAttached(view: View) {

    }

    override val layoutId: Int by lazy {
        R.layout.item_pager_card
    }
}
