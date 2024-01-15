package io.ganguo.sample.viewmodel.support.cache

import android.view.View
import android.view.ViewGroup
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.R
import io.ganguo.sample.database.table.SportTable

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/08
 *     desc   : Database Sample
 * </pre>
 */
class ActivityDatabaseSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {
    private val sports = arrayOf("足球", "篮球", "台球", "羽毛球", "网球", "乒乓球", "跑步", "俯卧撑", "引体向上", "瑜伽", "游泳")

    /**
     * 设置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_database_sample))
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    /**
     * 设置底部栏
     *
     * @param footer Function0<ViewGroup>
     */
    override fun initFooter(footer: () -> ViewGroup) {
        super.initFooter(footer)
        ItemFooterButtonVModel()
                .apply {
                    leftTitle.set(getString(R.string.str_insert_data))
                    rightTitle.set(getString(R.string.str_clean_data))
                    leftCallback = {
                        insertCacheData()
                    }
                    rightCallback = {
                        cleanCacheData()
                    }
                }.let {
                    ViewModelHelper.bind(footer.invoke(), this, it)
                }
    }

    /**
     * Insert data
     */
    private fun insertCacheData() {
        val viewModels = arrayListOf<ViewModel<*>>()
        sports.map {
            viewModels.add(ItemDatabaseSampleVModel(it))
            createDataTable(it)
        }.let {
            SportTable.insertAll(it)
        }
        adapter.addAll(viewModels)
    }

    /**
     * Create Data Table
     *
     * @param name
     * @return SportTable
     */
    private fun createDataTable(name: String): SportTable {
        return SportTable()
                .also {
                    it.name = name
                }
    }

    /**
     * Remove all data cache
     */
    private fun cleanCacheData() {
        SportTable.clear()
        adapter.clear()
    }

    override fun onViewAttached(view: View) {
        loadCacheFromDatabase()
    }

    /**
     * Load cache data
     */
    private fun loadCacheFromDatabase() {
        val caches = SportTable.queryAll()
        val viewModels = arrayListOf<ViewModel<*>>()
        caches.forEach {
            viewModels.add(ItemDatabaseSampleVModel(it.name))
        }
        adapter.addAll(viewModels)
    }
}