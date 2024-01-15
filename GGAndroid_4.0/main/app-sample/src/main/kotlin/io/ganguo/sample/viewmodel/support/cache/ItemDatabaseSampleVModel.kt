package io.ganguo.sample.viewmodel.support.cache

import android.view.View
import io.ganguo.mvvm.viewinterface.AdapterInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.sample.R
import io.ganguo.sample.database.table.SportTable
import io.ganguo.sample.databinding.ItemDatabaseSampleBinding
import io.support.recyclerview.adapter.diffuitl.IDiffComparator

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/08
 *     desc   : Database Sample ItemViewModel
 * </pre>
 */
class ItemDatabaseSampleVModel(val name: String) : ViewModel<AdapterInterface<ItemDatabaseSampleBinding>>(), IDiffComparator<String> {
    override val layoutId: Int = R.layout.item_database_sample

    override fun onViewAttached(view: View) {

    }

    /**
     * Click remove button
     *
     * @param view
     */
    fun actionRemoveData(view: View) {
        SportTable.removeByName(name)
        viewIF.adapter.remove(this)
    }

    override fun itemEquals(t: String): Boolean {
        return t == name
    }

    override fun getItem(): String {
        return name
    }
}