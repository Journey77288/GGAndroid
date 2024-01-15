package io.ganguo.sample.view.service.picker

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.service.picker.ActivityMediaPickerDemoVModel

/**
 * <pre>
 *   @author : leo
 *    time   : 2020/12/08
 *    desc   : 媒体文件选择库 - 配置demo
 * </pre>
 */
class MediaPickerDemoActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityMediaPickerDemoVModel>() {

    override fun createViewModel(): ActivityMediaPickerDemoVModel {
        return ActivityMediaPickerDemoVModel()
    }

    override fun onViewAttached(viewModel: ActivityMediaPickerDemoVModel) {
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, MediaPickerDemoActivity::class.java)
            context.startActivity(intent)
        }
    }
}
