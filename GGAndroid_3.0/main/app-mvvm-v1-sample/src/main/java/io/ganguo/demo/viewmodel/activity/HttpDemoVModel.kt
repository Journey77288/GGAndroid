package io.ganguo.demo.viewmodel.activity

import android.view.View
import com.scwang.smart.refresh.layout.api.RefreshLayout
import io.ganguo.demo.entity.one.MovieEntity
import io.ganguo.demo.http.api.impl.DouBanServiceImpl2
import io.ganguo.demo.http.helper.DouBanHttpHelper
import io.ganguo.demo.viewmodel.item.ItemOneListVModel
import io.ganguo.http2.error.Errors
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.log.core.Logger
import io.ganguo.rx.RxActions
import io.ganguo.rx.transformer.RxTransformer
import io.ganguo.rx.utils.emitItems
import io.ganguo.rx.utils.filterNotEmpty
import io.ganguo.viewmodel.pack.RxVMLifecycle
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding
import io.ganguo.viewmodel.pack.common.HFSRecyclerViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.functions.Functions
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

/**
 * <pre>
 * author : leo
 * time   : 2018/11/01
 * desc   : 网络请求Demo
 * </pre>
 */
open class HttpDemoVModel : HFSRecyclerViewModel<ActivityInterface<IncludeHfSwipeRecyclerBinding>>() {


    override fun onViewAttached(view: View) {
        simulateErrorTransformer()
        getDouBanMovies()
        getGankBanners()
    }

    /**
     * 获取豆瓣开源Api数据
     */
    private fun getDouBanMovies() {
        DouBanServiceImpl2
                .get()
                .getDouBanMovies()
                .subscribeOn(Schedulers.io())
                .compose(RxTransformer.errorToastProcessor())
                .compose(DouBanHttpHelper.errorProcessor())
                .compose(DouBanHttpHelper.dataProcessor())
                .compose(filterNotEmpty())
                .compose<MovieEntity>(emitItems())
                .map { e -> ItemOneListVModel(e) }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .map { itemOneListVModels -> itemOneListVModels }
                .doOnSuccess { itemOneListVModels ->
                    adapter.clear()
                    adapter.addAll(itemOneListVModels)
                    adapter.notifySetDataDiffChanged()
                }
                .doFinally { toggleEmptyView() }
                .compose(RxVMLifecycle.bindSingleViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable(javaClass.simpleName + "--getOneList--"))
                .addTo(lifecycleComposite)
    }


    /**
     * 获取Gank开源Banner api数据
     */
    private fun getGankBanners() {
        DouBanServiceImpl2
                .get()
                .getGankBanners()
                .subscribeOn(Schedulers.io())
                .compose(RxTransformer.errorToastProcessor())
                .compose(RxTransformer.errorLoggerProcessor())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    Logger.e("getDataFromDynamicDomain：$it")
                }
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable(javaClass.simpleName + "--getDataFromDynamicDomain--"))
                .addTo(lifecycleComposite)
    }

    /**
     * 模仿错误异常处理
     */
    private fun simulateErrorTransformer() {
        Observable
                .error<Throwable>(Errors.AuthorizationException(0, "你的登录信息过期了"))
                .compose(RxTransformer.errorToastProcessor())
                .compose(RxTransformer.errorLoggerProcessor())
                .subscribe()
                .addTo(lifecycleComposite)
    }


    override fun onRefresh(refreshLayout: RefreshLayout) {
        super.onRefresh(refreshLayout)
        getDouBanMovies()
    }
}
