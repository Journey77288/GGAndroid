package io.ganguo.sample.viewmodel.component

import android.view.View
import android.view.ViewGroup
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.R
import io.ganguo.sample.databinding.ActivitySideBarSampleBinding
import io.ganguo.sidebar.SideBarHintView
import io.ganguo.sidebar.SideBarView

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/20
 *     desc   : SideBar Sample
 * </pre>
 */
class ActivitySideBarSampleVModel(val sideBarVisible: Boolean)
    : ViewModel<ActivityInterface<ActivitySideBarSampleBinding>>(), SideBarView.OnSideBarListener {
    override val layoutId: Int = R.layout.activity_side_bar_sample
    private val cityListVModel: ComponentCityListVModel by lazy { ComponentCityListVModel() }
    private val letterArray: List<String> by lazy {
        val list = arrayListOf<String>()
        for (char in 'A'..'Z') {
            list.add(char.toString())
        }
        list
    }
    private val sideBarHintView: SideBarHintView by lazy { viewIF.binding.sbHint }
    private val sideBarView: SideBarView by lazy { viewIF.binding.sbView }

    override fun onViewAttached(view: View) {
        ViewModelHelper.bind(viewIF.binding.flyContent, this, cityListVModel)
        initHeader()
        initSideBar()
    }

    /**
     * 配置标题栏
     */
    private fun initHeader() {
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_sidebar_sample))
                .let {
                    ViewModelHelper.bind(viewIF.binding.flyHeader, this, it)
                }
    }

    /**
     * 配置SideBar
     */
    private fun initSideBar() {
        if (!sideBarVisible) {
            return
        }
        sideBarView.setLetters(letterArray)
        sideBarView.setSideBarListener(this)
        sideBarHintView.hideHint()
    }

    /**
     * SideBar点击状态变化回调
     *
     * @param sideBarView
     * @param isTouch
     */
    override fun onSideTouchState(sideBarView: SideBarView?, isTouch: Boolean) {
        if (!isTouch) {
            sideBarHintView.hideHint()
        }
    }

    /**
     * SideBar中选项点击回调
     *
     * @param sideBarView
     * @param position
     * @param currentY
     * @param selectedValue
     */
    override fun onSideSelected(sideBarView: SideBarView?, position: Int, currentY: Float, selectedValue: String?) {
        sideBarHintView.translationY = currentY
        sideBarHintView.showHint(selectedValue)
        cityListVModel.scrollToPosition(selectedValue)
    }
}