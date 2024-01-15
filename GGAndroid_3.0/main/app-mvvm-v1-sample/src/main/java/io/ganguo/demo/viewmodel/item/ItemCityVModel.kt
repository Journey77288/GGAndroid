package io.ganguo.demo.viewmodel.item

import android.view.View
import androidx.databinding.ObservableField
import io.ganguo.demo.R
import io.ganguo.demo.databinding.ItemCityBinding
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.sticky_header.StickyHeaderConfig
import io.ganguo.sticky_header.StickyHeaderHandler
import io.ganguo.viewmodel.core.BaseViewModel

/**
 * <pre>
 * author : leo
 * time   : 2019/02/26
 * desc   : 城市列表
</pre> *
 */
class ItemCityVModel(cityName: String) : BaseViewModel<ViewInterface<ItemCityBinding>>(), StickyHeaderHandler {
    override val layoutId: Int by lazy {
        R.layout.item_city
    }
    override val stickyHeaderConfig: StickyHeaderConfig by lazy {
        StickyHeaderHandler.newDefaultStickyHeaderConfig(StickyHeaderHandler.subFirstPinYin(cityName))
    }
    var cityName = ObservableField<String>()

    init {
        this.cityName.set(cityName)
    }

    override fun onViewAttached(view: View) {

    }


}
