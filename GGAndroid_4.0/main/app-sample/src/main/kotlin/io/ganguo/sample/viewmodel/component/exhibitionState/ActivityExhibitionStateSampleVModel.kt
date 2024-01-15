package io.ganguo.sample.viewmodel.component.exhibitionState

import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import io.ganguo.core.callback.TabMediatorWrapper
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.databinding.WidgetViewPagerBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.component.TabLayoutVModel
import io.ganguo.core.viewmodel.common.frame.HeaderContentFooterVModel
import io.ganguo.core.viewmodel.common.widget.ViewPager2VModel
import io.ganguo.mvvm.adapter.ViewModelAdapter
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.R
import io.ganguo.sample.bean.ExhibitionState
import io.ganguo.tab.callback.OnTabAdapterChooseListener

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/19
 *     desc   : Exhibition State Sample
 * </pre>
 */
class ActivityExhibitionStateSampleVModel : HeaderContentFooterVModel<ActivityInterface<FrameHeaderContentFooterBinding>,
        ViewPager2VModel<ViewInterface<WidgetViewPagerBinding>>, WidgetViewPagerBinding>() {
    override val contentLayoutId: Int = io.ganguo.core.R.layout.widget_view_pager
    override val contentViewModel: ViewPager2VModel<ViewInterface<WidgetViewPagerBinding>> by lazy {
        createViewPagerVModel()
    }
    private val adapter: ViewModelAdapter by lazy { contentViewModel.adapter }
    private lateinit var tabVModel: TabLayoutVModel

    /**
     * 创建ViewPagerVModel
     * @return ViewPager2VModel<ViewInterface<WidgetViewPagerBinding>>
     */
    private fun createViewPagerVModel(): ViewPager2VModel<ViewInterface<WidgetViewPagerBinding>> = let {
        val adapter = ViewModelAdapter(context, this)
        ViewPager2VModel<ViewInterface<WidgetViewPagerBinding>>(adapter)
                .also {
                    it.height(0)
                }
    }

    /**
     * 配置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_exhibition_state_sample))
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
        tabVModel = createStateTabVModel()
        ViewModelHelper.bind(header.invoke(), this, tabVModel)
    }

    /**
     * 创建Tab栏ViewModel
     *
     * @return TabLayoutVModel
     */
    private fun createStateTabVModel(): TabLayoutVModel = let {
        TabLayoutVModel
                .Builder()
                .backgroundColor(getColor(io.ganguo.resources.R.color.gray_light))
                .appendViewModel(createTabItemVModel(R.string.str_loading))
                .appendViewModel(createTabItemVModel(R.string.str_complete))
                .appendViewModel(createTabItemVModel(R.string.str_empty))
                .appendViewModel(createTabItemVModel(R.string.str_error))
                .appendViewModel(createTabItemVModel(R.string.str_network_error))
                .indicatorVisible(true)
                .indicatorHeight(getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_2))
                .indicatorColor(getColor(io.ganguo.resources.R.color.colorPrimary))
                .indicatorRadius(getDimension(io.ganguo.resources.R.dimen.dp_1))
                .distributeEvenly(false)
                .tabHeight(getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_50))
                .chooseListener(object : OnTabAdapterChooseListener() {
                    override fun onChooseTab(position: Int) {
                        contentViewModel.setCurrentItem(position, true)
                    }
                })
                .build()
    }

    /**
     * 创建Tab栏ItemViewModel
     *
     * @param titleRes
     * @return ItemExhibitionStateTabVModel
     */
    private fun createTabItemVModel(@StringRes titleRes: Int): ItemExhibitionStateTabVModel = let {
        ItemExhibitionStateTabVModel(getString(titleRes))
    }

    override fun onViewAttached(view: View) {
        initViewPager()
    }

    /**
     * 初始化ViewPager
     */
    private fun initViewPager() {
        with(adapter) {
            addAll(
                listOf(
                    PageExhibitionStateVModel(ExhibitionState.LOADING),
                    PageExhibitionStateVModel(ExhibitionState.COMPLETE),
                    PageExhibitionStateVModel(ExhibitionState.EMPTY),
                    PageExhibitionStateVModel(ExhibitionState.ERROR),
                    PageExhibitionStateVModel(ExhibitionState.NETWORK_ERROR)
                )
            )
        }
        val tabMediator = TabMediatorWrapper(tabVModel.getTabMediator())
        contentViewModel.addPageChangeCallback(tabMediator)
    }
}