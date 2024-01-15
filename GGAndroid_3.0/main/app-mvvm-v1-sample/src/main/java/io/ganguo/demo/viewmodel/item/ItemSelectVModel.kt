package io.ganguo.demo.viewmodel.item

import android.view.View

import io.ganguo.demo.R
import io.ganguo.demo.databinding.ItemSelectBinding
import io.ganguo.rx.selector.SelectHelper
import io.ganguo.rx.selector.SelectableView
import io.ganguo.rx.selector.SimpleSelectHelper
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.reactivex.functions.Consumer

/**
 * Created by Roger on 12/05/2017.
 */

class ItemSelectVModel(private val deleteAction: Consumer<ItemSelectVModel>?) : BaseViewModel<ViewInterface<ItemSelectBinding>>(), SelectableView<ItemSelectVModel>, SelectHelper.OnSelectListener {
    private val selectHelper: SelectHelper<ItemSelectVModel>

    init {
        this.selectHelper = SimpleSelectHelper(this, false)
        this.selectHelper.onSelectListener = this
    }

    override fun onViewAttached(view: View) {

    }

    fun onDeleteClick() {
        try {
            deleteAction?.accept(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun onCheckClick() {
        selectHelper.toggleSelect()
    }

    override fun getSelectHelper(): SelectHelper<ItemSelectVModel> {
        return selectHelper
    }

    override fun onSelected() {}

    override fun onUnselected() {}
    override val layoutId: Int by lazy { R.layout.item_select }
}
