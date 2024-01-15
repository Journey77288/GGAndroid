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
import io.ganguo.sample.view.support.cache.DatabaseSampleActivity
import io.ganguo.sample.view.support.cache.SharePreferenceSampleActivity
import io.ganguo.sample.viewmodel.ItemMenuVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/08
 *     desc   : Cache Sample
 * </pre>
 */
class ActivityCacheSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {

    /**
     * 设置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_cache_sample))
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    override fun onViewAttached(view: View) {
        with(adapter) {
            val viewModels = arrayListOf<ViewModel<*>>()
            viewModels.add(ItemMenuVModel.create(getString(R.string.str_cache_with_share_preference)) {
                SharePreferenceSampleActivity.start(context)
            })
            viewModels.add(ItemMenuVModel.create(getString(R.string.str_cache_with_database)) {
                DatabaseSampleActivity.start(context)
            })
            addAll(viewModels)
        }
    }
}