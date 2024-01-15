package io.ganguo.demo.viewmodel.item

import android.view.View

import io.ganguo.demo.R
import io.ganguo.demo.databinding.ViewSelectFooterBinding
import io.ganguo.rx.RxProperty
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.reactivex.Observable
import io.reactivex.functions.Action

/**
 * Created by Roger on 13/05/2017.
 */

class SelectDemoFooterVModel(isSelectAll: Observable<Boolean>, private val selectAllAction: Action? //全选 Action
                             , private val reverseAction: Action? //反选 Action
) : BaseViewModel<ViewInterface<ViewSelectFooterBinding>>() {
    val isSelectAll: RxProperty<Boolean> = RxProperty(isSelectAll)

    override fun onViewAttached(view: View) {

    }

    /**
     * 点击全选
     */
    fun onSelectAllClick() {
        try {
            selectAllAction?.run()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 点击反选
     */
    fun onReverseClick() {
        try {
            reverseAction?.run()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    override val layoutId: Int by lazy {
        R.layout.view_select_footer
    }
}
