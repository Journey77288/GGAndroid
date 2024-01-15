package io.ganguo.sample.view.component

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.component.ActivityComponentSampleVModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/05
 *   @desc   : Component Sample
 * </pre>
 */
class ComponentSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityComponentSampleVModel>() {
    override fun createViewModel(): ActivityComponentSampleVModel {
        return ActivityComponentSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityComponentSampleVModel) {
    }

    companion object {

        /**
         * start ComponentSampleActivity
         * @param context Context
         */
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, ComponentSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}
