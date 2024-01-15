package io.ganguo.demo.viewmodel.activity

import android.annotation.SuppressLint
import android.view.View

import com.scwang.smart.refresh.layout.api.RefreshLayout

import java.util.concurrent.TimeUnit

import io.ganguo.demo.viewmodel.item.ItemDemoVModel
import io.ganguo.state.loading.ILoadingHandler
import io.ganguo.state.loading.LoadingHelper
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.utils.util.Randoms
import io.ganguo.viewmodel.pack.common.HFSRecyclerViewModel
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.pack.RxVMLifecycle
import io.ganguo.viewmodel.pack.helper.LoadingVModelHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * 自定义HFSRecyclerViewModel页面Loading
 * Created by leo on 2018/9/14.
 */
class CustomLoadingStyleVModel : HFSRecyclerViewModel<ActivityInterface<IncludeHfSwipeRecyclerBinding>>() {

    override fun onViewAttached(view: View) {
        onRefresh(smartRefreshLayout)
    }


    override fun onRefresh(refreshLayout: RefreshLayout) {
        super.onRefresh(refreshLayout)
        adapter.clear()
        loadDelay(2)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        super.onLoadMore(refreshLayout)
        loadDelay(1)
    }


    /**
     * function：load data
     *
     * @param delaySecond
     */
    @SuppressLint("CheckResult")
    private fun loadDelay(delaySecond: Int) {
        Observable.just(20)
                .delay(delaySecond.toLong(), TimeUnit.SECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { toggleEmptyView() }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(addIssueItem())
    }

    /**
     * function：add Issue Item
     *
     * @return
     */
    private fun addIssueItem(): Consumer<Int> {
        return Consumer { count ->
            for (i in 0 until count) {
                adapter.add(createItemDemoVModel())
            }
            adapter.notifySetDataDiffChanged()
            toggleEmptyView()
        }
    }


    /**
     * function: create Item ViewModel
     *
     * @return
     */
    private fun createItemDemoVModel(): BaseViewModel<*> {
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
