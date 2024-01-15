package io.ganguo.sample.view.widget

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.widget.ActivityWidgetSampleVModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/12
 *   @desc   : Widget Sample Activity
 * </pre>
 */
class WidgetSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityWidgetSampleVModel>() {
    override fun createViewModel(): ActivityWidgetSampleVModel {
        return ActivityWidgetSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityWidgetSampleVModel) {
    }


    companion object {

        /**
         * start WidgetSampleActivity
         * @param context Context
         */
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, WidgetSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}
