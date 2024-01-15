package io.ganguo.sample.viewmodel

import android.view.View
import android.view.ViewGroup
import io.ganguo.core.callback.TabMediatorWrapper
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.databinding.WidgetViewPagerBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.component.PageLoadingVModel
import io.ganguo.core.viewmodel.common.component.TabLayoutVModel
import io.ganguo.core.viewmodel.common.frame.HeaderContentFooterVModel
import io.ganguo.core.viewmodel.common.widget.ViewPager2VModel
import io.ganguo.mvvm.adapter.ViewModelAdapter
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.R
import io.ganguo.tab.callback.OnTabAdapterChooseListener

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   : Tab Sample
 * </pre>
 */
class ActivityTabSampleVModel : HeaderContentFooterVModel<ActivityInterface<FrameHeaderContentFooterBinding>, ViewPager2VModel<ViewInterface<WidgetViewPagerBinding>>, WidgetViewPagerBinding>() {
    private lateinit var tabVModel: TabLayoutVModel
    override val contentLayoutId: Int = io.ganguo.core.R.layout.widget_view_pager
    override val contentViewModel: ViewPager2VModel<ViewInterface<WidgetViewPagerBinding>> by lazy {
        createViewPagerVModel()
    }
    private val adapter: ViewModelAdapter by lazy {
        contentViewModel.adapter
    }

    init {
        addLoadingViewCreator(PageLoadingVModel(this))
    }

    /**
     * 配置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_tab_sample))
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    override fun onViewAttached(view: View) {
        initViewPager()
    }


    /**
     * create ViewPager2VModel， Height must be set to 0
     * @return ViewPager2VModel<ViewInterface<WidgetViewPagerBinding>>
     */
    private fun createViewPagerVModel(): ViewPager2VModel<ViewInterface<WidgetViewPagerBinding>> = let {
        ViewPager2VModel<ViewInterface<WidgetViewPagerBinding>>(ViewModelAdapter(context, this))
                .apply {
                    height(0)
                }
    }


    /**
     * init Content ViewModel
     * @param viewGroup ViewGroup
     */
    private fun initViewPager() {
        adapter.addAll(
            listOf(
                PageHomeVModel(),
                PageHomeVModel(),
                PageHomeVModel(),
                PageHomeVModel(),
            )
        )
        var tabMediator = TabMediatorWrapper(tabVModel.getTabMediator())
        contentViewModel.addPageChangeCallback(tabMediator)
    }

    /**
     * TabLayoutVModel init
     * @param footer Function0<ViewGroup>
     */
    override fun initFooter(footer: () -> ViewGroup) {
        super.initFooter(footer)
        tabVModel = TabLayoutVModel
                .Builder()
                .backgroundColor(getColor(io.ganguo.resources.R.color.blue_light_translucent))
                .appendViewModel(ItemMainTabVModel(getString(R.string.str_home)))
                .appendViewModel(ItemMainTabVModel(getString(R.string.str_mall)))
                .appendViewModel(ItemMainTabVModel(getString(R.string.str_event)))
                .appendViewModel(ItemMainTabVModel(getString(R.string.str_me)))
                .indicatorVisible(false)
                .distributeEvenly(true)
                .tabHeight(getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_50))
                .chooseListener(object : OnTabAdapterChooseListener() {
                    override fun onChooseTab(position: Int) {
                        contentViewModel.setCurrentItem(position)
                    }
                })
                .build()
        ViewModelHelper.bind(footer.invoke(), this, tabVModel)
    }

}
