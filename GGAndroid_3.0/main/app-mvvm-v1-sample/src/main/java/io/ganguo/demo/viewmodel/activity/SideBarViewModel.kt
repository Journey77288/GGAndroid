package io.ganguo.demo.viewmodel.activity

import android.view.View
import io.ganguo.demo.R
import io.ganguo.demo.databinding.ActivitySideBarBinding
import io.ganguo.demo.viewmodel.page.SideCityListVModel
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper
import io.guguo.sidebar.SideBarHintView
import io.guguo.sidebar.SideBarView

/**
 * <pre>
 * author : leo
 * time   : 2019/02/25
 * desc   : Side Sample ViewModel
 * </pre>
 */
open class SideBarViewModel : BaseViewModel<ActivityInterface<ActivitySideBarBinding>>(), SideBarView.OnSideBarListener {
    override val layoutId: Int by lazy { R.layout.activity_side_bar }
    private var cityListVModel: SideCityListVModel = SideCityListVModel()
    private val letterArray: List<String> by lazy {
        var list = arrayListOf<String>()
        for (char in 'A'..'Z') {
            list.add(char.toString())
        }
        list
    }
    private val sideBarHintView: SideBarHintView
        get() = viewInterface.binding.sideHint

    private val sideBarView: SideBarView
        get() = viewInterface.binding.sbView

    override fun onViewAttached(view: View) {
        ViewModelHelper.bind(viewInterface.binding.includeHfRecycler, this, cityListVModel)
        sideBarView.setLetters(letterArray)
        sideBarView.setOnSideBarListener(this)
    }


    override fun onSideTouchState(sideBarView: SideBarView, isTouch: Boolean) {
        if (!isTouch) {
            sideBarHintView.hideHint()
        }
    }

    override fun onSideSelected(sideBarView: SideBarView?, position: Int, currentY: Float, selectedValue: String) {
        sideBarHintView.translationY = currentY
        sideBarHintView.showHint(selectedValue)
        cityListVModel?.onScrollListPosition(selectedValue)
    }

}
