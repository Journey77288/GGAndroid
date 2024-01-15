package io.ganguo.sample.viewmodel.component

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import io.commponent.indicator.CircleDotIndicator
import io.component.banner.listener.BannerPageChangeAdapterListener
import io.component.banner.transformer.AlphaPageTransformer
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.BannerViewModel
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.component.IndicatorVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.core.viewmodel.common.widget.ImageViewModel
import io.ganguo.core.viewmodel.common.widget.TextViewModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.image.enums.ImageShapeType

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/05
 *   @desc   : Banner Sample
 * </pre>
 */
class ActivityBannerSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {
    /**
     * 设置标题栏
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        HeaderTitleVModel
                .sampleTitleVModel(viewIF.activity, "Banner Sample")
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    override fun onViewAttached(view: View) {
        val viewModels = arrayListOf<ViewModel<*>>()
        // Common Banner Effects
        viewModels.add(TextViewModel.sampleExplainVModel("Common Banner Effects"))
        val circleIndicator = createIndicatorVModel(getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_4), getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_4))
        viewModels.add(createBannerVModel(circleIndicator))
        viewModels.add(circleIndicator)

        val roundCornerIndicator = createIndicatorVModel(getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_9), getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_3))
        viewModels.add(createBannerVModel(roundCornerIndicator))
        viewModels.add(roundCornerIndicator)

        // MeiZu Banner Effects
        viewModels.add(TextViewModel.sampleExplainVModel("MeiZu Banner Effects"))
        viewModels.add(createMeiZuBannerVModel())


        // Gallery Banner Effects
        viewModels.add(TextViewModel.sampleExplainVModel("Gallery Banner Effects"))
        viewModels.add(createGalleryBannerVModel())

        adapter.addAll(viewModels)
    }


    /**
     * create IndicatorVModel
     * @return IndicatorVModel
     */
    private fun createIndicatorVModel(widthPx: Int, heightPx: Int): IndicatorVModel = let {
        val indicator = CircleDotIndicator(3, getColor(io.ganguo.resources.R.color.gray_dark), getColor(io.ganguo.resources.R.color.red))
                .apply {
                    this.widthPx = widthPx
                    this.heightPx = heightPx
                }
        IndicatorVModel()
                .indicator(indicator)
                .indicatorSpace(getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_4))
                .orientation(RecyclerView.HORIZONTAL)
                .apply {
                    width(ViewGroup.LayoutParams.MATCH_PARENT)
                    margin(0, getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_10), 0, getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_10))
                }
    }


    /**
     * Common Banner Effects
     * @return BaseViewModel<*>
     */
    private fun createBannerVModel(indicatorVModel: IndicatorVModel? = null): BannerViewModel = let {
        BannerViewModel(this)
                .pageTransformer(AlphaPageTransformer())
                .shufflingIntervalMillisecond(3000)
                .setBannerChangeListener(object : BannerPageChangeAdapterListener() {
                    override fun onPageSelected(position: Int) {
                        indicatorVModel?.selectedIndicator(position)
                    }
                })
                .apply {
                    addAll(
                        listOf(
                            createPageVModel("https://pic1.zhimg.com/v2-4bba972a094eb1bdc8cbbc55e2bd4ddf_1440w.jpg?source=172ae18b", Color.BLUE),
                            createPageVModel("https://pic4.zhimg.com/v2-3b4fc7e3a1195a081d0259246c38debc_720w.jpg?source=172ae18b", Color.RED),
                            createPageVModel("https://image.nbd.com.cn/uploads/articles/images/673466/500352700_banner.jpg", Color.GREEN)
                        )
                    )
                    heightRes(io.ganguo.resources.R.dimen.dp_150)
                }
    }

    /**
     * MeiZu Banner Effects
     * @return BaseViewModel<*>
     */
    private fun createMeiZuBannerVModel(): BannerViewModel = let {
        val with = getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_30)
        createBannerVModel()
                .toMeiZuEffect(with, 0.9f)
    }


    /**
     * Gallery Banner Effects
     * @return BaseViewModel<*>
     */
    private fun createGalleryBannerVModel(): BannerViewModel = let {
        val with = getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_20)
        createBannerVModel()
                .toGalleryEffect(with, with, with / 2, 0.9f)
    }


    /**
     * Banner pager
     * @return ViewModel<*>
     */
    private fun createPageVModel(url: String, @ColorInt color: Int): ViewModel<*> = let {
        ImageViewModel(ImageShapeType.SQUARE)
                .apply {
                    scaleType(ImageView.ScaleType.CENTER_CROP)
                    width(ViewGroup.LayoutParams.MATCH_PARENT)
                    height(ViewGroup.LayoutParams.MATCH_PARENT)
                    backgroundColor(color)
                    url(url)
                }
    }


}
