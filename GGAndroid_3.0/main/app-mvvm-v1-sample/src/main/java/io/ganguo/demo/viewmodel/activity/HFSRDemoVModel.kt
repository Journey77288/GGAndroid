package io.ganguo.demo.viewmodel.activity

import android.view.View
import android.view.ViewGroup
import com.scwang.smart.refresh.layout.api.RefreshLayout
import io.ganguo.demo.viewmodel.item.ItemDemoVModel
import io.ganguo.demo.viewmodel.item.ItemFooterVModel
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.log.core.Logger
import io.ganguo.rx.RxActions
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.utils.util.Randoms
import io.ganguo.viewmodel.pack.common.HFSRecyclerViewModel
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.pack.RxVMLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Roger on 6/22/16.
 */
class HFSRDemoVModel : HFSRecyclerViewModel<ActivityInterface<IncludeHfSwipeRecyclerBinding>>() {

    override fun initFooter(container: ViewGroup) {
        super.initFooter(container)
        val testFooterViewModel = ItemFooterVModel(onClear())
        ViewModelHelper.bind(container, this, testFooterViewModel)
    }


    /**
     * Init data here
     */
    override fun onViewAttached(view: View) {
        loadDelay(2, false)
    }


    override fun onRefresh(refreshLayout: RefreshLayout) {
        super.onRefresh(refreshLayout)
        loadDelay(2, true)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        super.onLoadMore(refreshLayout)
        loadDelay(0, false)
    }

    /**
     * Clear data
     */
    fun onClear(): Consumer<View> {
        return Consumer {
            val size = adapter.itemCount
            adapter.clear()
            adapter.notifyItemRangeRemoved(0, size)
            toggleEmptyView()
        }
    }

    private fun loadDelay(delaySecond: Int, isRefresh: Boolean) {
        Observable.just(15)
                .delay(delaySecond.toLong(), TimeUnit.SECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxVMLifecycle.bindViewModel(this))
                .doOnNext {
                    if (isRefresh) {
                        adapter.clear()
                        adapter.notifyDataSetChanged()
                    }
                }
                .doOnNext(addViewModel())
                .doOnComplete {
                    if (!isAttach) {
                        return@doOnComplete
                    }
                    toggleEmptyView()
                }
                .doOnComplete {
                    toggleEmptyView()
                }
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable())
    }


    /**
     * function: add ViewModels
     *
     * @return
     */
    fun addViewModel(): Consumer<Int> {
        return Consumer { count ->
            val j = adapter.itemCount
            for (i in 0 until count) {
                adapter.add(createItemDemoVModel())
            }
            adapter.notifyItemRangeInserted(j, count!!)
        }
    }

    /**
     * function: create Item ViewModel
     *
     * @return
     */
    fun createItemDemoVModel(): BaseViewModel<*> {
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
