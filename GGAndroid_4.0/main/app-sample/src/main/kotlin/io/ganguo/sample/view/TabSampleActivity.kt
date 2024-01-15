package io.ganguo.sample.view

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.ActivityTabSampleVModel

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   : Tab Sample
 * </pre>
 */
class TabSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityTabSampleVModel>() {
    override fun createViewModel(): ActivityTabSampleVModel {
        return ActivityTabSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityTabSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, TabSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}
