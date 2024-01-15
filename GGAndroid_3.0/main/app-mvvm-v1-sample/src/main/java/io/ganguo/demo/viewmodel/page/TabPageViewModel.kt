package io.ganguo.demo.viewmodel.page

import android.view.View
import android.view.ViewGroup

import com.scwang.smart.refresh.layout.api.RefreshLayout

import io.ganguo.demo.viewmodel.item.ItemDemoVModel
import io.ganguo.demo.viewmodel.item.ItemFooterVModel
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.utils.util.Randoms
import io.ganguo.viewmodel.pack.common.HFSRecyclerViewModel
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper
import io.reactivex.functions.Consumer

/**
 * Created by leo on 16/7/11.
 * TabLayout - 对应ViewModel
 */
class TabPageViewModel : HFSRecyclerViewModel<ActivityInterface<IncludeHfSwipeRecyclerBinding>>() {

    override fun initFooter(container: ViewGroup) {
        super.initFooter(container)
        val testFooterViewModel = ItemFooterVModel(onClear())
        ViewModelHelper.bind(container, this, testFooterViewModel)
    }


    override fun onViewAttached(view: View) {
        recyclerView.isFocusableInTouchMode = false
        loadDelay(true)
    }


    override fun onLoadMore(refreshLayout: RefreshLayout) {
        super.onLoadMore(refreshLayout)
        loadDelay(false)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        super.onRefresh(refreshLayout)
        loadDelay(true)
    }

    /**
     * Clear data
     */
    private fun onClear(): Consumer<View> {
        return Consumer {
            adapter.clear()
            adapter.notifySetDataDiffChanged()
            toggleEmptyView()
        }
    }

    private fun loadDelay(isRefresh: Boolean) {
        if (isRefresh) {
            adapter.clear()
        }
        for (i in 0..19) {
            adapter.add(newItemDemoVModel())
        }
        adapter.notifySetDataDiffChanged()
        toggleEmptyView()
    }

    /**
     * 生成 Item ViewModel
     *
     * @return
     */
    private fun newItemDemoVModel(): BaseViewModel<*> {
        val content = Randoms.getRandomCapitalLetters(12)
        return ItemDemoVModel<String>()
                .setBtnText("click")
                .setDataObj(content)
                .setContent(content)
                .setClickAction {
                    ToastHelper.showMessage("click")
                }
    }

}
