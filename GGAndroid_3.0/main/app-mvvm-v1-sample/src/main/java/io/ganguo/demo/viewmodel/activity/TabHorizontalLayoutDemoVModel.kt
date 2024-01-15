package io.ganguo.demo.viewmodel.activity

import android.view.View

import io.ganguo.demo.R
import io.ganguo.demo.databinding.ActivityTabLayoutDemoBinding
import io.ganguo.demo.viewmodel.page.TabPageViewModel
import io.ganguo.demo.viewmodel.item.ItemTabVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.log.core.Logger
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.viewmodel.pack.common.CommonViewPagerVModel
import io.ganguo.viewmodel.pack.common.TabLayoutViewModel

/**
 * update by leo on 16/7/11.
 * TabLayoutDemo - viewmodel
 */
open class TabHorizontalLayoutDemoVModel : BaseViewModel<ActivityInterface<ActivityTabLayoutDemoBinding>>() {
    override val layoutId: Int by lazy {
        R.layout.activity_tab_layout_demo
    }

    private val viewPagerVModel: CommonViewPagerVModel by lazy {
        newViewPagerVModel()
    }


    override fun onViewAttached(view: View) {
        //bind ViewPage
        ViewModelHelper.bind(viewInterface.binding.includeViewpager, this, viewPagerVModel)
        //bind tab
        val tabViewModel = newTabViewModel()
        ViewModelHelper.bind(viewInterface.binding.includeTablayout, this, tabViewModel)
        //get tab item viewModel
        val tabIconViewModel = tabViewModel.getTabItemViewModel<ItemTabVModel>(0)
        Logger.e("tabViewModel:title:" + tabIconViewModel.title!!)
    }


    /**
     * new CommonViewPagerVModel
     *
     * @return
     */
    private fun newViewPagerVModel(): CommonViewPagerVModel {
        return CommonViewPagerVModel(
                listOf<BaseViewModel<*>>(
                        TabPageViewModel(),
                        TabPageViewModel(),
                        TabPageViewModel(),
                        TabPageViewModel(),
                        TabPageViewModel(),
                        TabPageViewModel(),
                        TabPageViewModel(),
                        TabPageViewModel(),
                        TabPageViewModel(),
                        TabPageViewModel()
                )
        )
    }


    /**
     * new TabLayoutViewModel
     *
     * @return
     */
    private fun newTabViewModel(): TabLayoutViewModel {
        return TabLayoutViewModel.Builder(this)
                .indicatorColor(getColor(R.color.colorAccent), getColor(R.color.colorAccent))
                .indicatorWidth(getDimensionPixelOffset(R.dimen.dp_30))
                .indicatorHeight(getDimensionPixelOffset(R.dimen.tab_height))
                .indicatorRadius(getDimensionPixelOffset(R.dimen.dp_2))
                .distributeEvenly(false)
                .indicatorVisible(true)
                .indicatorWidthWrapContent(true)
                .viewPagerSmoothScroll(true)
                .viewPagerSmoothScrollAnimation(true)
                .backgroundColor(getColor(R.color.gray))
                .layoutId(R.layout.include_tab_horizontal_layout_view_model)
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "tab1测试长度"))
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "tab2"))
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "tab3"))
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "tab4"))
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "tab5"))
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "tab6"))
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "tab7"))
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "tab8"))
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "tab9"))
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "tab10"))
                .bindControlScrollViewPager(viewPagerVModel.viewPager)
                .build()
    }

}
