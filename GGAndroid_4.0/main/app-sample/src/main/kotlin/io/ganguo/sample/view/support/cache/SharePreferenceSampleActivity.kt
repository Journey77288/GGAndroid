package io.ganguo.sample.view.support.cache

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.cache.ActivitySharePreferenceVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/08
 *     desc   : SharePreference Sample
 * </pre>
 */
class SharePreferenceSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivitySharePreferenceVModel>() {
    override fun createViewModel(): ActivitySharePreferenceVModel {
        return ActivitySharePreferenceVModel()
    }

    override fun onViewAttached(viewModel: ActivitySharePreferenceVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, SharePreferenceSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}