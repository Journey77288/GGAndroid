package io.ganguo.demo.viewmodel.activity

import android.view.View
import android.view.ViewGroup
import com.scwang.smart.refresh.layout.api.RefreshLayout
import io.ganguo.demo.R
import io.ganguo.demo.viewmodel.item.ItemBannerDemoVModel
import io.ganguo.demo.viewmodel.item.ItemTabVModel
import io.ganguo.demo.viewmodel.page.TabPageViewModel
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.state.loading.ILoadingHandler
import io.ganguo.state.loading.LoadingHelper
import io.ganguo.utils.util.Colors
import io.ganguo.utils.util.postDelayed
import io.ganguo.viewmodel.pack.common.CommonViewPagerVModel
import io.ganguo.viewmodel.pack.common.CoordinatorAppBarVModel
import io.ganguo.viewmodel.pack.common.TabLayoutViewModel
import io.ganguo.viewmodel.pack.common.loading.LoadingMaterialProgressVModel
import io.ganguo.viewmodel.databinding.IncludeCoordinatorLayoutAppBarBinding
import io.ganguo.viewmodel.pack.helper.LoadingVModelHelper
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 *
 *
 * 1、CoordinatorAppBarVModel Demo
 * 2、通过CoordinatorLayout和AppBarLayout可以实现原生折叠/计算颜色渐变效果
 *
 *
 * Created by leo on 2018/7/13.
 */
open class CoordinatorAppBarDemoVModel : CoordinatorAppBarVModel<ActivityInterface<IncludeCoordinatorLayoutAppBarBinding>>(), ILoadingHandler {
    override val loadingHelper: LoadingHelper by lazy {
        LoadingVModelHelper.bindLoadingView(loadingContainer, this, LoadingMaterialProgressVModel())
    }

    private lateinit var bannerViewModel: ItemBannerDemoVModel
    private val viewPagerVModel: CommonViewPagerVModel by lazy {
        newViewPagerVModel()
    }


    override fun onViewAttached(view: View) {
        enableAppBarElevation()
        showLoading()
        context.postDelayed(1000) { hideLoading() }
    }


    override fun initAppBarHeader(container: ViewGroup) {
        super.initAppBarHeader(container)
        bannerViewModel = ItemBannerDemoVModel()
        ViewModelHelper.bind(container, this, bannerViewModel)
    }

    /**
     * function: init AppBarContent
     *
     * @param container
     */
    override fun initAppBarContent(container: ViewGroup) {
        super.initAppBarContent(container)
        ViewModelHelper.bind(container, this, newTabViewModel())
    }

    /**
     * function: init Content
     *
     * @param container
     */
    override fun initContent(container: ViewGroup) {
        super.initContent(container)
        ViewModelHelper.bind(container, this, viewPagerVModel)
    }


    override fun onAppBarLayoutExpandState(isExpand: Boolean, factor: Float) {
        super.onAppBarLayoutExpandState(isExpand, factor)
        //可根据滑动的距离，在此处计算动态设置一些View颜色/透明度
        val color = Colors.evaluatorViewColor(factor, getColor(R.color.transparent), getColor(R.color.blue_dark))
        bannerViewModel.rootView.setBackgroundColor(color)
    }


    /**
     * 生成 TabLayoutViewModel
     *
     * @return
     */
    private fun newTabViewModel(): TabLayoutViewModel {
        return TabLayoutViewModel.Builder(this)
                .indicatorColor(getColor(R.color.colorAccent), getColor(R.color.colorAccent))
                .indicatorWidth(getDimensionPixelOffset(R.dimen.dp_30))
                .indicatorHeight(getDimensionPixelOffset(R.dimen.tab_height))
                .distributeEvenly(true)
                .indicatorVisible(true)
                .indicatorWidthWrapContent(false)
                .viewPagerSmoothScroll(true)
                .viewPagerSmoothScrollAnimation(true)
                .backgroundColor(getColor(R.color.white))
                .layoutId(R.layout.include_tab_horizontal_layout_view_model)
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "tab1"))
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "tab2"))
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "tab3"))
                .bindControlScrollViewPager(viewPagerVModel.viewPager)
                .build()
    }

    /**
     * 生成 CommonViewPagerVModel
     *
     * @return
     */
    private fun newViewPagerVModel(): CommonViewPagerVModel {
        return CommonViewPagerVModel(
                listOf<BaseViewModel<*>>(
                        TabPageViewModel(),
                        TabPageViewModel(),
                        TabPageViewModel()
                )
        )
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        super.onRefresh(refreshLayout)
        refreshLayout.finishRefresh()
    }

}
