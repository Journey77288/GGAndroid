package io.ganguo.sample.view.widget

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.widget.layout.ActivityCornerFrameLayoutSampleVModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/13
 *   @desc   : CorerFrameLayout Sample
 * </pre>
 */
class CorerFrameLayoutSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityCornerFrameLayoutSampleVModel>() {
    override fun createViewModel(): ActivityCornerFrameLayoutSampleVModel {
        return ActivityCornerFrameLayoutSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityCornerFrameLayoutSampleVModel) {
    }


    companion object {
        /**
         * start CorerFrameLayoutSampleActivity
         * @param context Context
         */
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, CorerFrameLayoutSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}
