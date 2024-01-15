package io.ganguo.sample.viewmodel

import android.view.View
import android.view.ViewGroup
import com.scwang.smart.refresh.layout.api.RefreshLayout
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.PageLoadingVModel
import io.ganguo.core.viewmodel.common.frame.HFSRecyclerVModel
import io.ganguo.log.core.Logger
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.support.recyclerview.adapter.diffuitl.IDiffComparator

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/14
 *   @desc   : 首页列表
 * </pre>
 */
class PageHomeVModel : HFSRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>(), IDiffComparator<Int> {

    init {
        addLoadingViewCreator(PageLoadingVModel(this))
    }

    override fun onViewAttached(view: View) {
        showLoadingView()
        addTestData()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        addTestData()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        adapter.clear()
        addTestData()
    }

    private fun addTestData() {
        val newData = adapter.currentList.toMutableList()
        for (i in adapter.itemCount..adapter.itemCount + 20) {
            newData.add(ItemTitleVModel(i.toString()))
        }
        adapter.addAll(newData)
        toggleEmptyView()
    }


    override fun initHeader(header: () -> ViewGroup) {
        ViewModelHelper.bind(header.invoke(), this, ItemTitleVModel("标题栏"))
    }

    override fun initFooter(footer: () -> ViewGroup) {
        ViewModelHelper.bind(footer.invoke(), this, ItemTitleVModel("底部菜单"))
    }

    override fun itemEquals(t: Int): Boolean {
        return t == getItem()
    }

    override fun getItem(): Int {
        return hashCode()
    }

}
