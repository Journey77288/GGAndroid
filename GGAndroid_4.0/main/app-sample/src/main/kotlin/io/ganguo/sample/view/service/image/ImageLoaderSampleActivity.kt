package io.ganguo.sample.view.service.image

import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.bean.Keys
import io.ganguo.sample.viewmodel.service.image.ActivityImageLoaderSampleVModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/10/13
 *   @desc   : 图片加载示例
 * </pre>
 */
class ImageLoaderSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityImageLoaderSampleVModel>() {


    override fun createViewModel(): ActivityImageLoaderSampleVModel {
        val title = intent.getStringExtra(Keys.Intent.Common.DATA)
        return ActivityImageLoaderSampleVModel(title.orEmpty())
    }


    override fun onViewAttached(viewModel: ActivityImageLoaderSampleVModel) {
    }

}
