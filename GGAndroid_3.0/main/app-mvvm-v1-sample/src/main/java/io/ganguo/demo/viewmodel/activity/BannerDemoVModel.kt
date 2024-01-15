package io.ganguo.demo.viewmodel.activity

import android.view.View
import io.ganguo.demo.viewmodel.item.ItemBannerDemoVModel
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.viewmodel.pack.common.HFRecyclerViewModel
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding

/**
 * Created by leo on 2017/4/28.
 * 广告轮播 - demo
 */
class BannerDemoVModel : HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>>() {

    /**
     * function: add ViewModel
     *
     * @param view
     */
    override fun onViewAttached(view: View) {
        adapter.add(ItemBannerDemoVModel())
        adapter.notifyDataSetChanged()
        toggleEmptyView()
    }


}
