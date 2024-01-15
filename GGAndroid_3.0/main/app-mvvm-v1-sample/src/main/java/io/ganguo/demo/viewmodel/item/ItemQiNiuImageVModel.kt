package io.ganguo.demo.viewmodel.item

import androidx.databinding.ObservableField
import android.view.View

import io.ganguo.demo.R
import io.ganguo.demo.databinding.ItemQiniuImageBinding
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.viewinterface.ViewInterface

/**
 *
 *
 * 七牛demo - item ViewModel
 *
 * Created by leo on 2018/7/30.
 */
class ItemQiNiuImageVModel(imageUrl: String, index: String) : BaseViewModel<ViewInterface<ItemQiniuImageBinding>>() {
    var imageUrl = ObservableField<String>()
    var index = ObservableField<String>()

    init {
        this.imageUrl.set(imageUrl)
        this.index.set(index)
    }


    override fun onViewAttached(view: View) {

    }

    override val layoutId: Int by lazy {
        R.layout.item_qiniu_image
    }
}
