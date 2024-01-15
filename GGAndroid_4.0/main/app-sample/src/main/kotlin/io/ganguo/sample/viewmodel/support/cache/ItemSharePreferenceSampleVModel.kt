package io.ganguo.sample.viewmodel.support.cache

import android.view.View
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.KeyboardUtils
import io.ganguo.cache.sp.SPHelper
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.sample.R
import io.ganguo.sample.bean.Keys
import io.ganguo.sample.databinding.ItemSharePreferenceSampleBinding

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/08
 *     desc   : Cache Sample ItemViewModel
 * </pre>
 */
class ItemSharePreferenceSampleVModel : ViewModel<ViewInterface<ItemSharePreferenceSampleBinding>>() {
    override val layoutId: Int = R.layout.item_share_preference_sample
    val input = ObservableField<String>()

    init {
        loadInputCache()
    }

    private fun loadInputCache() {
        val cache = SPHelper.getString(Keys.Cache.Common.DATA)
        input.set(cache)
    }

    override fun onViewAttached(view: View) {

    }

    /**
     * Click outside
     *
     * @param view
     */
    fun actionClickOutside(view: View) {
        KeyboardUtils.hideSoftInput(view)
    }

    /**
     * Click save button
     *
     * @param view
     */
    fun actionSaveInput(view: View) {
        SPHelper.putString(Keys.Cache.Common.DATA, input.get().orEmpty())
    }

    /**
     * Click clean cache button
     *
     * @param view
     */
    fun actionCleanCache(view: View) {
        SPHelper.remove(Keys.Cache.Common.DATA)
    }

    /**
     * Click clean all cache button
     *
     * @param view
     */
    fun actionCleanAllCache(view: View) {
        SPHelper.clearAll()
    }
}