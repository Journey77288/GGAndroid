package io.ganguo.sample.view.support.cache

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.cache.ActivityCacheSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/08
 *     desc   : Cache Sample
 * </pre>
 */
class CacheSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityCacheSampleVModel>() {
    override fun createViewModel(): ActivityCacheSampleVModel {
        return ActivityCacheSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityCacheSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, CacheSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}