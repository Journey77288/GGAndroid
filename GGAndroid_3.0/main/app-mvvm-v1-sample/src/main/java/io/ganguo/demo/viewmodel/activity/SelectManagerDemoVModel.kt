package io.ganguo.demo.viewmodel.activity

import android.view.View
import android.view.ViewGroup

import io.ganguo.demo.viewmodel.item.ItemSelectVModel
import io.ganguo.demo.viewmodel.item.SelectDemoFooterVModel
import io.ganguo.viewmodel.pack.RxVMLifecycle
import io.ganguo.rx.selector.MultiSelectManager
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.viewmodel.pack.common.HFRecyclerViewModel
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * SelectManagerDemoVModel
 * Created by Roger on 12/05/2017.
 */
class SelectManagerDemoVModel : HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>>() {
    private val selectManager: MultiSelectManager<ItemSelectVModel> = MultiSelectManager()

    init {
        // 设置最大选择个数
        //        this.selectManager.setMaxSelected(5);
        // 设置最小选择个数
        //        this.selectManager.setMinSelected(2);
        //         设置当超出最大选中数量时，自动选中最新的 item, 并取消选中最旧的 item
        //        this.selectManager.setAutoSelect(true);
    }

    /**
     * function: delete 事件
     */
    private val onDeleteAction = Consumer<ItemSelectVModel> { itemSelectVModel ->
        val index = adapter.indexOf(itemSelectVModel)
        adapter.remove(itemSelectVModel)
        adapter.notifyItemRemoved(index)
        selectManager.remove(itemSelectVModel)
    }

    /**
     * function: 选中全部
     */
    private val selectAllAction: Action
        get() = Action {
            if (selectManager.isAllSelectedProperty.get()!!) {
                selectManager.cancelSelected()
            } else {
                selectManager.selectAll()
            }
        }

    /**
     * function: 反选
     *
     * @return
     */
    private val reverseSelectAction: Action
        get() = Action { selectManager.reverseSelect() }


    override fun initFooter(container: ViewGroup) {
        super.initFooter(container)
        val footerVModel = SelectDemoFooterVModel(
                selectManager.isAllSelectedProperty,
                selectAllAction,
                reverseSelectAction)
        ViewModelHelper.bind(container, this, footerVModel)
    }

    override fun onViewAttached(view: View) {
        observeOnMaxSelected()
        initItemVModel()
    }

    /**
     *  订阅最大选择数量
     */
    private fun observeOnMaxSelected() {
        selectManager.onMaxSelectedEvent
                .compose(RxVMLifecycle.bindViewModel(this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { maxCount -> ToastHelper.showMessage("已选中最大数量" + maxCount!!) }
    }

    /**
     * init Item ViewModel
     */
    private fun initItemVModel() {
        for (i in 0..6) {
            val viewModel = ItemSelectVModel(onDeleteAction)
            selectManager.add(viewModel)
            adapter.add(viewModel)
        }
        adapter.notifyDataSetChanged()
        toggleEmptyView()
    }
}
