package io.ganguo.sample.view.widget

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.widget.switch_button.ActivitySwitchIOSButtonSampleVModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/12
 *   @desc   : Switch Button Sample
 * </pre>
 */
class SwitchIOSButtonSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivitySwitchIOSButtonSampleVModel>() {
    override fun createViewModel(): ActivitySwitchIOSButtonSampleVModel {
        return ActivitySwitchIOSButtonSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivitySwitchIOSButtonSampleVModel) {
    }

    companion object {
        /**
         * start ImageEngineSampleActivity
         * @param context Context
         */
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, SwitchIOSButtonSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}
