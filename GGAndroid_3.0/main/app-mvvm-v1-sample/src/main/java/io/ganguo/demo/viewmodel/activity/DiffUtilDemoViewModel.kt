package io.ganguo.demo.viewmodel.activity

import android.view.View
import android.view.ViewGroup
import com.scwang.smart.refresh.layout.api.RefreshLayout
import io.ganguo.demo.R
import io.ganguo.demo.entity.CommonDemoEntity
import io.ganguo.demo.view.activity.DiffUtilDemoActivity
import io.ganguo.demo.viewmodel.item.ItemDiffUtilDemoVModel
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.log.core.Logger
import io.ganguo.viewmodel.pack.common.HFSRecyclerViewModel
import io.ganguo.viewmodel.pack.common.item.ItemSampleVModel
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 *
 *
 * DiffUtil 刷新数据工具类 Demo
 *
 * Created by leo on 2018/8/27.
 */
open class DiffUtilDemoViewModel : HFSRecyclerViewModel<ActivityInterface<IncludeHfSwipeRecyclerBinding>>() {
    private val text = arrayOf("Java", "C++", "GoLang", "PHP", "C", "PWA", "Vue", "Python", "Flutter", "Kotlin", "AA", "BB", "CC", "DD", "EE", "FF")

    override fun onViewAttached(view: View) {
        init()
    }


    override fun initHeader(container: ViewGroup) {
        super.initHeader(container)
        setEnableHeaderElevation(false)
        ViewModelHelper.bind(container, this, (viewInterface.activity as DiffUtilDemoActivity).newHeaderVModel())
        ViewModelHelper.bind(container, this, newRefreshVModel())
    }


    override fun initFooter(container: ViewGroup) {
        super.initFooter(container)
        setEnableFooterElevation(false)
        ViewModelHelper.bind(container, this, newLoadMoreVModel())
    }


    /**
     * init data
     */
    private fun init() {
        for (i in text.indices) {
            adapter.add(newDiffItemVModel(text[i]))
        }
        adapter.notifySetDataDiffChanged()
        toggleEmptyView()
    }


    /**
     * 生成 Diff item viewModel
     *
     * @param text
     * @return
     */
    private fun newDiffItemVModel(text: String): BaseViewModel<*> {
        return ItemDiffUtilDemoVModel(CommonDemoEntity())
                .apply {
                    this.text = text
                    removeAction = {
                        adapter.remove(it)
                        adapter.notifySetDataDiffChanged()
                    }
                }
    }


    /**
     * 生成 Refresh ViewModel
     *
     * @return
     */
    private fun newRefreshVModel(): BaseViewModel<*> {
        return ItemSampleVModel()
                .setAction {
                    adapter.clear()
                    init()
                }
                .setText("模拟下拉刷新")
                .setBg(R.color.red)
    }


    /**
     * 生成 LoadMore ViewModel
     *
     * @return
     */
    private fun newLoadMoreVModel(): BaseViewModel<*> {
        return ItemSampleVModel()
                .setAction { init() }
                .setText("模拟上拉加载")
    }


    override fun onLoadMore(refreshLayout: RefreshLayout) {
        super.onLoadMore(refreshLayout)
        Logger.e("DiffUtilDemoViewModel：onLoadMore")
        init()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        super.onRefresh(refreshLayout)
        adapter.clear()
        init()
    }

}
