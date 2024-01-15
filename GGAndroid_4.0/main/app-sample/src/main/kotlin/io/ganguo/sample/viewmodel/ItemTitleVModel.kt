package io.ganguo.sample.viewmodel

import android.view.View
import io.support.recyclerview.adapter.diffuitl.IDiffComparator
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.sample.databinding.ItemTestTitleBinding
import io.ganguo.sample.R
import io.ganguo.appcompat.Toasts

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/14
 *   @desc   :
 * </pre>
 */
class ItemTitleVModel(val test: String) : ViewModel<ViewInterface<ItemTestTitleBinding>>(), IDiffComparator<String> {
    override val layoutId: Int = R.layout.item_test_title

    override fun onViewAttached(view: View) {
    }


    fun actionToast() {
        Toasts.show("测试")
    }

    override fun getItem(): String {
        return test
    }

    override fun itemEquals(t: String): Boolean {
        return t == getItem()
    }


}
