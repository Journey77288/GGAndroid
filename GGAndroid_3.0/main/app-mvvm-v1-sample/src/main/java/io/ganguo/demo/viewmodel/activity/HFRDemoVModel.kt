package io.ganguo.demo.viewmodel.activity

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import io.ganguo.demo.viewmodel.item.ItemDemoVModel
import io.ganguo.demo.viewmodel.item.ItemFooterVModel
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.utils.util.Randoms
import io.ganguo.viewmodel.pack.common.HFRecyclerViewModel
import io.ganguo.viewmodel.pack.common.HeaderItemViewModel
import io.ganguo.viewmodel.pack.common.HeaderViewModel
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.pack.RxVMLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Roger on 6/23/16.
 */

class HFRDemoVModel : HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>>() {

    override fun initHeader(container: ViewGroup) {
        super.initHeader(container)
        val headerViewModel = HeaderViewModel.Builder()
                .appendItemCenter(HeaderItemViewModel.TitleItemViewModel("HFRecycler Demo"))
                .appendItemLeft(HeaderItemViewModel.BackItemViewModel(viewInterface.activity))
                .build()
        ViewModelHelper.bind(container, headerViewModel)
    }

    override fun initFooter(container: ViewGroup) {
        super.initFooter(container)

        ViewModelHelper.bind(container, ItemFooterVModel(onClear()))
    }


    override fun onViewAttached(view: View) {
        loadDelay(2)
    }

    /**
     * Clear data
     */
    private fun onClear(): Consumer<View> {
        return Consumer {
            val size = adapter.itemCount
            adapter.clear()
            adapter.notifyItemRangeRemoved(0, size)
            toggleEmptyView()
        }
    }

    @SuppressLint("CheckResult")
    private fun loadDelay(delaySecond: Int) {
        Observable.just(20)
                .delay(delaySecond.toLong(), TimeUnit.SECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { toggleEmptyView() }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(addIssue())
    }

    private fun addIssue(): Consumer<Int> {
        return Consumer { count ->
            // check attach
            if (!isAttach) {
                return@Consumer
            }
            for (i in 0 until count) {
                adapter.add(newItemDemoVModel())
            }
            adapter.notifySetDataDiffChanged()
        }
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
                .setClickAction{
                    ToastHelper.showMessage("click")
                }
    }
}
