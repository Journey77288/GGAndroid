package io.ganguo.sample.view

import android.content.Context
import android.content.Intent
import io.ganguo.app.helper.activity.ActivityHelper
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.ActivitySampleVModel
import io.ganguo.utils.Apps

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/10/12
 *   @desc   : 首页入口
 * </pre>
 */
class SampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivitySampleVModel>() {

    override fun createViewModel(): ActivitySampleVModel {
        return ActivitySampleVModel()
    }

    override fun onViewAttached(viewModel: ActivitySampleVModel) {
    }

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, SampleActivity::class.java))
        }
    }
}
