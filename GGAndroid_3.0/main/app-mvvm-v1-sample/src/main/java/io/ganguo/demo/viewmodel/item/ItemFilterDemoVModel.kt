package io.ganguo.demo.viewmodel.item

import androidx.databinding.ObservableField
import android.view.View

import io.ganguo.demo.R
import io.ganguo.demo.databinding.ItemFilterDemoBinding
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.viewinterface.AdapterInterface

/**
 * Created by leo on 2016/12/21.
 */
class ItemFilterDemoVModel(title: String) : BaseViewModel<io.ganguo.viewmodel.core.viewinterface.AdapterInterface<ItemFilterDemoBinding>>() {
    override val layoutId: Int by lazy { R.layout.item_filter_demo }
    var title = ObservableField<String>()

    init {
        this.title.set(title)
    }


    override fun onViewAttached(view: View) {

    }


}
