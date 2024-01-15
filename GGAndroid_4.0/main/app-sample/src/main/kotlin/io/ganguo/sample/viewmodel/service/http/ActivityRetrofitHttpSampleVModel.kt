package io.ganguo.sample.viewmodel.service.http

import android.view.View
import android.view.ViewGroup
import io.ganguo.appcompat.Toasts
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.component.PageLoadingVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.core.viewmodel.common.widget.TextViewModel
import io.ganguo.http.gg.helper.GGHttpHelper
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.rxjava.printThrowable
import io.ganguo.sample.http.api.impl.SettingServiceImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.internal.functions.Functions
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/13
 *   @desc   : Retrofit Http Sample
 * </pre>
 */
class ActivityRetrofitHttpSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {

    init {
        addLoadingViewCreator(PageLoadingVModel(this))
    }

    /**
     * 设置标题栏
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        HeaderTitleVModel
                .sampleTitleVModel(viewIF.activity, "Retrofit Http Sample")
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }


    override fun onViewAttached(view: View) {
        showLoadingView()
        getContactUs()
    }


    /**
     * 获取联系信息
     */
    private fun getContactUs() {
        SettingServiceImpl
                .get()
                .getContactUs()
                .delay(2, TimeUnit.SECONDS)
                .compose(GGHttpHelper.errorProcessor())
                .compose(GGHttpHelper.asResultProcessor())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    adapter.addAll(
                        listOf(
                            TextViewModel.sampleExplainVModel(it.toString()),
                            TextViewModel.sampleExplainVModel(it.toString()),
                            TextViewModel.sampleExplainVModel("新增") {
                                adapter.addAll(listOf(
                                    TextViewModel.sampleExplainVModel("fdfdfdfd"),
                                    TextViewModel.sampleExplainVModel("还是会辅导费")
                                ))
                            }
                        )
                    )
                    Toasts.show("网络请求成功！！")
                }
                .doOnError {
                    Toasts.show("网络请求失败:${it.message}！！")
                }
                .doFinally {
                    toggleEmptyView()
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--getContactUs--"))
                .addTo(lifecycleComposite)
    }
}
