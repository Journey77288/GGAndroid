package io.ganguo.sample.viewmodel

import android.view.View
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.sample.R
import io.ganguo.sample.databinding.ItemMainTabBinding

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/14
 *   @desc   : Main Tab
 * </pre>
 */
class ItemMainTabVModel(var tabMenu: String) : ViewModel<ViewInterface<ItemMainTabBinding>>() {
    override fun onViewAttached(view: View) {
    }
    override val layoutId: Int by lazy {
        R.layout.item_main_tab
    }
}