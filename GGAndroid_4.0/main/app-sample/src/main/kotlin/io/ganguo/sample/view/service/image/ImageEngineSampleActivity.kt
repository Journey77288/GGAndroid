package io.ganguo.sample.view.service.image

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.service.image.ActivityImageEngineSampleVModel

/**
 * <pre>
 *   @author : leo
 *    time   : 2020/10/17
 *    desc   : 图片加载引擎配置Demo
 * </pre>
 */
class ImageEngineSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityImageEngineSampleVModel>() {

    override fun createViewModel(): ActivityImageEngineSampleVModel {
        return ActivityImageEngineSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityImageEngineSampleVModel) {
    }


    companion object {

        /**
         * start ImageEngineSampleActivity
         * @param context Context
         */
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, ImageEngineSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}
