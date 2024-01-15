package io.ganguo.demo.viewmodel.activity

import android.view.View
import io.ganguo.demo.R
import io.ganguo.demo.databinding.ActivityLazyLoadDemoBinding
import io.ganguo.demo.viewmodel.item.ItemTabVModel
import io.ganguo.demo.viewmodel.page.PageLazyLoadVModel
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.viewmodel.pack.common.LazyViewPagerVModel
import io.ganguo.viewmodel.pack.common.TabLayoutViewModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/17
 *     desc   : 页面数据懒加载 - Demo
 * </pre>
 */
class LazyLoadDemoVModel : BaseViewModel<ActivityInterface<ActivityLazyLoadDemoBinding>>() {
    override val layoutId: Int by lazy {
        R.layout.activity_lazy_load_demo
    }
    private val viewPagerVModel: LazyViewPagerVModel by lazy {
        newViewPagerVModel()
    }

    override fun onViewAttached(view: View) {
        ViewModelHelper.bind(viewInterface.binding.includeViewpager, this, viewPagerVModel)
        ViewModelHelper.bind(viewInterface.binding.includeTablayout, this, newTabViewModel())

    }


    /**
     * 懒加载ViewPagerViewModel
     * @return
     */
    private fun newViewPagerVModel(): LazyViewPagerVModel {
        return LazyViewPagerVModel(
                arrayListOf(
                        PageLazyLoadVModel(),
                        PageLazyLoadVModel(),
                        PageLazyLoadVModel(),
                        PageLazyLoadVModel(),
                        PageLazyLoadVModel()
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
                .distributeEvenly(true)
                .indicatorVisible(true)
                .indicatorWidthWrapContent(true)
                .viewPagerSmoothScroll(true)
                .viewPagerSmoothScrollAnimation(true)
                .backgroundColor(getColor(R.color.gray))
                .layoutId(R.layout.include_tab_horizontal_layout_view_model)
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "页面1"))
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "页面2"))
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "页面3"))
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "页面4"))
                .appendViewModel(ItemTabVModel(R.drawable.selector_tab_icon, "页面5"))
                .bindControlScrollViewPager(viewPagerVModel.viewPager)
                .build()
    }

}