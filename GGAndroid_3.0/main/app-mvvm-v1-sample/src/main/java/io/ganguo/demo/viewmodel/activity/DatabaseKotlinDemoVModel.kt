package io.ganguo.demo.viewmodel.activity

import android.view.View
import android.view.ViewGroup
import com.scwang.smart.refresh.layout.api.RefreshLayout
import io.ganguo.demo.R
import io.ganguo.demo.database.table.SportKotlinTable
import io.ganguo.demo.view.activity.DatabaseKotlinDemoActivity
import io.ganguo.demo.viewmodel.item.ItemDemoVModel
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.log.core.Logger
import io.ganguo.rx.RxActions
import io.ganguo.rx.utils.emitItems
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.viewmodel.pack.common.HFSRecyclerViewModel
import io.ganguo.viewmodel.pack.common.HeaderViewModel
import io.ganguo.viewmodel.pack.common.TextViewModel
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.pack.RxVMLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.functions.Functions
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

/**
 * ObjectBox -- 数据库Demo Kotlin版本
 * Created by leo on 2018/11/10.
 */
open class DatabaseKotlinDemoVModel : HFSRecyclerViewModel<ActivityInterface<IncludeHfSwipeRecyclerBinding>>() {
    private val tableIds: MutableList<Long> = mutableListOf()
    override fun onViewAttached(view: View) {
        queryAllSport()
    }


    override fun initSmartRefresh(refreshLayout: RefreshLayout) {
        super.initSmartRefresh(refreshLayout)
        setEnableLoadMore(false)
        setEnableRefresh(false)
    }


    override fun initHeader(container: ViewGroup) {
        super.initHeader(container)
        val headerMenu = HeaderViewModel.Builder()
                .appendItemLeft(newInsertSportVModel())
                .appendItemRight(newDeleteVModel())
                .appendItemCenter(newQueryAllSportVModel())
                .build()
        var activity = viewInterface.activity as DatabaseKotlinDemoActivity
        ViewModelHelper.bind(container, this, activity.newHeaderVModel())
        ViewModelHelper.bind(container, this, headerMenu)
    }


    /**
     *  插入多条数据
     *
     * @return
     */
    private fun newInsertSportVModel(): BaseViewModel<*> {
        return TextViewModel.Builder()
                .paddingRightRes(R.dimen.dp_10)
                .paddingLeftRes(R.dimen.dp_10)
                .paddingBottomRes(R.dimen.dp_10)
                .paddingTopRes(R.dimen.dp_10)
                .textColorRes(R.color.white)
                .content("插入数据")
                .onClickListener {
                    SportKotlinTable.insertTestSport()
                }
                .build()
    }


    /**
     *  删除全部数据
     *
     * @return
     */
    private fun newDeleteVModel(): BaseViewModel<*> {
        return TextViewModel.Builder()
                .paddingRightRes(R.dimen.dp_10)
                .paddingLeftRes(R.dimen.dp_10)
                .paddingBottomRes(R.dimen.dp_10)
                .textColorRes(R.color.white)
                .paddingTopRes(R.dimen.dp_10)
                .content("删除全部数据")
                .onClickListener {
                    SportKotlinTable.clear()
                    tableIds.clear()
                    adapter.clear()
                    adapter.notifySetDataDiffChanged()
                }
                .build()
    }

    /**
     * function: 查询全部数据
     *
     * @return
     */
    private fun newQueryAllSportVModel(): BaseViewModel<*> {
        return TextViewModel.Builder()
                .content("查询全部数据")
                .paddingRightRes(R.dimen.dp_10)
                .paddingLeftRes(R.dimen.dp_10)
                .paddingBottomRes(R.dimen.dp_10)
                .textColorRes(R.color.white)
                .paddingTopRes(R.dimen.dp_10)
                .onClickListener { queryAllSport() }
                .build()
    }

    /**
     *  查询全部运动数据
     *
     * @return
     */
    private fun queryAllSport() {
        SportKotlinTable
                .asRxQuery()
                .subscribeOn(Schedulers.io())
                .flatMap {
                    ofMapItemViewModel(it)
                }
                .compose(RxVMLifecycle.bindViewModel<Any>(this))
                .subscribe(Functions.emptyConsumer<Any>(), RxActions.printThrowable(javaClass.simpleName + "--queryAllSpotData--"))
                .addTo(lifecycleComposite)
    }


    /**
     * 注：处理新增ViewModel，必须新建一个Observable来处理查询到的数据，否则无法将数据更新到UI
     *    因为主Observable已经被挂起，一直监听数据库更新，直到Observable被关闭为止，才会执行doFinally等操作
     *
     * @param table
     */
    private fun ofMapItemViewModel(table: List<SportKotlinTable>): Observable<MutableList<ItemDemoVModel<SportKotlinTable>>> {
        return Observable
                .just(table)
                .compose(emitItems())
                .filter {
                    !tableIds.contains(it.id)
                }
                .map {
                    tableIds.add(it.id)
                    newItemDemoVModel(it)
                }
                .toList()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { itemDemoVModels ->
                    Logger.e("ofMapItemViewModel:${itemDemoVModels.size}")
                    adapter.addAll(itemDemoVModels)
                    adapter.notifySetDataDiffChanged()
                }
                .doOnComplete {
                    toggleEmptyView()
                }
    }


    /**
     * function: create Item ViewModel
     *
     * @return
     */
    private fun newItemDemoVModel(table: SportKotlinTable): ItemDemoVModel<SportKotlinTable> {
        return ItemDemoVModel<SportKotlinTable>()
                .setBtnText("删除")
                .setDataObj(table)
                .setContent(table.name)
                .setClickAction {
                    removeSport(it)
                }
    }


    /**
     * 删除单条数据
     * @param vModel
     */
    private fun removeSport(vModel: ItemDemoVModel<SportKotlinTable>) {
        vModel.data?.let {
            if (it.remove()) {
                tableIds.remove(vModel.data!!.id)
                adapter.remove(vModel)
                ToastHelper.showMessage("remove success!!!")
            } else {
                ToastHelper.showMessage("remove fail!!!")
            }
        }
    }

}
