package io.ganguo.viewmodel.pack.base.viewmodel

import com.scwang.smart.refresh.layout.api.RefreshLayout
import io.ganguo.http2.core.use.response.paging.IPagingHandler
import io.ganguo.http2.core.use.response.paging.PagingHelper
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/17
 *     desc   : 支持接口分页处理的HFSRecyclerVModel，配合RxPageHelper使用，
 * </pre>
 */
abstract class BasePageHFSRecyclerVModel<T : ViewInterface<IncludeHfSwipeRecyclerBinding>> : BaseHFSRecyclerVModel<T>(), IPagingHandler {
    override val pageHelper: PagingHelper by lazy {
        PagingHelper()
    }


    override fun onRefresh(refreshLayout: RefreshLayout) {
        super.onRefresh(refreshLayout)
        pageHelper.pageReset()

        //分页请求api数据demo
        //getProductList(this)
    }

    override fun toggleEmptyView() {
        super.toggleEmptyView()
        setEnableLoadMore(!pageHelper.isLastPage())
    }

    /**
     * RxPageHelper 及 PageHelper 使用Demo
     */
    /* private fun getProductList(iPageHandler: IPageHandler) {
         getApiServiceProxy()
                  //1、将需要请求的数据页码，分页长度传递给api
                 .getProductList(iPageHandler.pageHelper.nextPage(), iPageHandler.pageHelper.pageSize())
                 .compose(HttpPaginationExtraResponseHandler())
                 .map {
                     it.result
                 }
                 //2、绑定RxPageHelper处理分页数据,进行分页累加、是否是第一页等数据更新操作
                 .compose(RxPageHelper.handlerPage(this))
                 .map {
                     it.content
                 }.subscribeOn(Schedulers.io())
                 .flatMapIterable {
                     it
                 }
                 .map {
                     ItemProductVModel(it)
                 }
                 .toList()
                 .toObservable()
                 .observeOn(AndroidSchedulers.mainThread())
                 .doOnNext {
                 //3、是第一页则先清空列表，再添加数据
                     if (pageHelper.isFirstPage()) {
                         adapter.clear()
                     }
                     adapter.addAll(it)
                     adapter.notifyDiffUtilSetDataChanged()
                 }
                 .doOnComplete {
                     toggleEmptyView()
                     LoadingHelper.hideMaterLoading()
                 }
                 .compose(RxVMLifecycle.bindViewModel(this))
                 .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--getProductList--"))
                 .addTo(compositeDisposable)
     }*/
}