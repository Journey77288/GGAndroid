package io.ganguo.demo.viewmodel.item

import android.view.View
import android.widget.ImageView

import io.ganguo.banner.view.IndicatorView

import io.ganguo.demo.R
import io.ganguo.demo.databinding.ItemBannerDemoBinding
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.utils.helper.ResHelper
import io.ganguo.viewmodel.pack.common.BannerViewModel
import io.ganguo.viewmodel.pack.common.ImageViewModel
import io.ganguo.viewmodel.pack.common.IndicatorViewModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 *
 *
 * Banner ViewModel Demo
 *
 * Created by leo on 2018/10/25.
 */
open class ItemBannerDemoVModel : BaseViewModel<ViewInterface<ItemBannerDemoBinding>>() {
    override val layoutId: Int by lazy {
        R.layout.item_banner_demo
    }
    private var imgs = arrayOf("http://www.51pptmoban.com/d/file/2015/11/27/2123f7686d354b4d1b67b99a7f657747.jpg", "http://pic1.win4000.com/wallpaper/6/57bd626e9cd08.jpg", "https://c.mql5.com/1/64/LES.jpg")
    private var indicatorViewModel: IndicatorViewModel? = null

    override fun onViewAttached(view: View) {
        val bannerViewModel = createBannerVModel()
        indicatorViewModel = getIndicatorVModel(3)
        ViewModelHelper.bind(viewInterface.binding.includeBanner, this, bannerViewModel)
        ViewModelHelper.bind(viewInterface.binding.includeIndicator, this, indicatorViewModel!!)
    }


    /**
     * function: getBannerVModel
     *
     * @return
     */
    protected fun createBannerVModel(): BannerViewModel {
        return BannerViewModel.Builder(this@ItemBannerDemoVModel)
                .setDurationTime(5000)
                .setAutoPlay(true)
                .setScrollDuration(2000)
                .addItemViewModel(getBannerItemVModel(imgs[0]))
                .addItemViewModel(getBannerItemVModel(imgs[1]))
                .addItemViewModel(getBannerItemVModel(imgs[2]))
                .setBannerHeightRes(R.dimen.dp_200)
                .setScrollPositionListener { position -> indicatorViewModel!!.indicatorView.setCurrentPosition(position) }
                .build()
    }

    /**
     * function: Banner item
     *
     * @param url
     * @return
     */
    protected fun getBannerItemVModel(url: String): BaseViewModel<*> {
        return ImageViewModel.Builder()
                .width(ImageViewModel.MATCH_PARENT)
                .height(ImageViewModel.MATCH_PARENT)
                .url(url)
                .scaleType(ImageView.ScaleType.CENTER_CROP)
                .build()
    }

    /**
     * function: Banner 指示器
     *
     * @return
     */
    protected fun getIndicatorVModel(count: Int): IndicatorViewModel {
        val dp5 = ResHelper.getDimensionPixelOffsets(R.dimen.dp_5)
        val dp8 = ResHelper.getDimensionPixelOffsets(R.dimen.dp_8)
        val builder = IndicatorView.Builder()
        builder.setIndicatorCount(count)
        builder.setIndicatorDrawableRes(R.drawable.selector_indicator)
        builder.setIndicatorHeight(dp5)
        builder.setIndicatorWidth(dp5)
        builder.setIndicatorSpace(dp8)
        return IndicatorViewModel(builder)
    }


}
