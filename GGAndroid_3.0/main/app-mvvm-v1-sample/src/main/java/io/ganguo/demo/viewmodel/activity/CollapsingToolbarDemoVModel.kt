package io.ganguo.demo.viewmodel.activity

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import io.ganguo.demo.R
import io.ganguo.demo.view.activity.CollapsingToolbarDemoActivity
import io.ganguo.demo.viewmodel.item.ItemBannerDemoVModel
import io.ganguo.demo.viewmodel.item.ItemTabVModel
import io.ganguo.demo.viewmodel.page.TabPageViewModel
import io.ganguo.state.loading.ILoadingHandler
import io.ganguo.state.loading.LoadingHelper
import io.ganguo.utils.util.AppBars
import io.ganguo.utils.util.Colors
import io.ganguo.utils.util.postDelayed
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.viewmodel.databinding.IncludeCoordinatorCollapsingToolbarBinding
import io.ganguo.viewmodel.pack.common.CollapsingToolbarViewModel
import io.ganguo.viewmodel.pack.common.CommonViewPagerVModel
import io.ganguo.viewmodel.pack.common.TabLayoutViewModel
import io.ganguo.viewmodel.pack.common.loading.LoadingMaterialProgressVModel
import io.ganguo.viewmodel.pack.helper.LoadingVModelHelper

/**
 * CollapsingToolbar 折叠联动效果 Demo
 * Created by leo on 2018/9/14.
 */
open class CollapsingToolbarDemoVModel : CollapsingToolbarViewModel<ActivityInterface<IncludeCoordinatorCollapsingToolbarBinding>>(), ILoadingHandler{
    override val loadingHelper: LoadingHelper by lazy {
        LoadingVModelHelper.bindLoadingView(loadingContainer, this, LoadingMaterialProgressVModel())
    }
    private val viewPagerVModel: CommonViewPagerVModel by lazy {
        CommonViewPagerVModel(
                mutableListOf<BaseViewModel<*>>(
                        TabPageViewModel(),
                        TabPageViewModel(),
                        TabPageViewModel()
                )
        )
    }
    private val tabViewModel: TabLayoutViewModel by lazy {
        newTabLayoutVModel()
    }

    /**
     * 生成TabLayout ViewModel
     */
    private fun newTabLayoutVModel(): TabLayoutViewModel {
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
                .bindControlScrollViewPager(this.viewPagerVModel.viewPager)
                .build()
    }


    override fun onViewAttached(view: View) {
        enableAppBarElevation()
        showLoading()
        context.postDelayed(1000) { hideLoading() }
    }


    override fun initToolbar(toolbarLayout: CollapsingToolbarLayout, toolbar: Toolbar) {
        AppBars.statusBarTranslucent(viewInterface.activity)
        toolbar.setPadding(0, AppBars.getStatusBarHeight(context), 0, 0)
        ViewModelHelper.bind(toolbar, this, (viewInterface.activity as CollapsingToolbarDemoActivity).newHeaderVModel())
    }


    override fun initAppBarHeader(container: ViewGroup) {
        super.initAppBarHeader(container)
        ViewModelHelper.bind(container, this, ItemBannerDemoVModel())
    }

    /**
     * function: init AppBarContent
     *
     * @param container
     */
    override fun initAppBarContent(container: ViewGroup) {
        super.initAppBarContent(container)
        ViewModelHelper.bind(container, this, tabViewModel)
    }

    /**
     * function: init Content
     *
     * @param container
     */
    override fun initContent(container: ViewGroup) {
        super.initContent(container)
        ViewModelHelper.bind(container, this, this.viewPagerVModel)
    }


    override fun onAppBarLayoutExpandState(isExpand: Boolean, factor: Float) {
        super.onAppBarLayoutExpandState(isExpand, factor)
        val color = Colors.evaluatorViewColor(factor, getColor(R.color.transparent), getColor(R.color.colorPrimary))
        toolbar.setBackgroundColor(color)
    }


    override fun onRefresh(refreshLayout: RefreshLayout) {
        super.onRefresh(refreshLayout)
        smartRefreshLayout!!.finishRefresh()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
