package io.ganguo.sample.viewmodel

import android.view.View
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.sample.R
import io.ganguo.sample.databinding.ItemMenuBinding
import io.support.recyclerview.adapter.diffuitl.IDiffComparator

/**
 * <pre>
 *   @author : leo
 *   @time : 2020/10/13
 *   @desc : 通用菜单样式
 * </pre>
 */
class ItemMenuVModel private constructor(val menu: String, private val clickFunc: (String) -> Unit) : ViewModel<ViewInterface<ItemMenuBinding>>(), IDiffComparator<String> {
    override val layoutId: Int = R.layout.item_menu
    override fun onViewAttached(view: View) {
    }

    /**
     * menu click
     */
    fun actionClicked() {
        clickFunc.invoke(menu)
    }

    override fun itemEquals(t: String): Boolean {
        return t == getItem()
    }

    override fun getItem(): String {
        return menu
    }


    companion object {


        /**
         * create ItemMenuVModel
         * @param menu String
         * @param clickFunc Function1<String, Unit>
         * @return ItemMenuVModel
         */
        @JvmStatic
        fun create(menu: String, clickFunc: (String) -> Unit): ItemMenuVModel = ItemMenuVModel(menu, clickFunc)
    }
}
