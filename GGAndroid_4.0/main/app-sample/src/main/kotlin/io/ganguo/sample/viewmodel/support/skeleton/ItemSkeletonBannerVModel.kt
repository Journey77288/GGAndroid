package io.ganguo.sample.viewmodel.support.skeleton

import android.view.View
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.sample.R
import io.ganguo.sample.databinding.ItemSkeletonBannerBinding

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/04/28
 *     desc   : Skeleton View Banner
 * </pre>
 */
class ItemSkeletonBannerVModel : ViewModel<ViewInterface<ItemSkeletonBannerBinding>>() {
    override val layoutId: Int = R.layout.item_skeleton_banner
    val bannerImg = "https://pic1.zhimg.com/v2-4bba972a094eb1bdc8cbbc55e2bd4ddf_1440w.jpg?source=172ae18b"

    override fun onViewAttached(view: View) {

    }
}