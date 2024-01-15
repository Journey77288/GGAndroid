package io.ganguo.demo.viewmodel.activity

import android.view.View

import io.ganguo.demo.R
import io.ganguo.demo.databinding.ActivityViewPagerDemoBinding
import io.ganguo.demo.entity.ImageEntity
import io.ganguo.demo.viewmodel.item.ItemPagerCardVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.viewmodel.pack.common.CommonViewPagerVModel

/**
 * update by leo on 2018/07/06.
 * CommonViewPagerVModel - demo
 */
class ViewPagerDemoVModel : BaseViewModel<ActivityInterface<ActivityViewPagerDemoBinding>>() {
    override val layoutId: Int by lazy {
        R.layout.activity_view_pager_demo
    }

    private var pagerVModel: CommonViewPagerVModel? = null

    override fun onViewAttached(view: View) {
        ViewModelHelper.bind(viewInterface.binding.includeViewpager, this, getPagerVModel()!!)
    }


    /**
     * function：get CommonViewPagerVModel
     *
     * @return
     */
    private fun getPagerVModel(): CommonViewPagerVModel? {
        if (pagerVModel == null) {
            val image = ImageEntity("kk", "http://d.hiphotos.baidu.com/zhidao/pic/item/4e4a20a4462309f7ab5f674a700e0cf3d7cad6b2.jpg")
            pagerVModel = CommonViewPagerVModel(
                    listOf(
                            ItemPagerCardVModel(image),
                            ItemPagerCardVModel(image),
                            ItemPagerCardVModel(image),
                            ItemPagerCardVModel(image)
                    ))
                    .setViewPagerSmoothScroll(true)
                    .setPageMargin(getDimensionPixelOffset(R.dimen.dp_5))
                    .setClipChildren(false)
                    .setClipPadding(false)
                    .setPaddingLeft(getDimensionPixelOffset(R.dimen.dp_25))
                    .setPaddingRight(getDimensionPixelOffset(R.dimen.dp_25))

        }
        return pagerVModel
    }


}
