package io.ganguo.sample.view.support.skeleton

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.skeleton.ActivitySkeletonSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/04/28
 *     desc   : Skeleton Sample
 * </pre>
 */
class SkeletonSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivitySkeletonSampleVModel>() {
    override fun createViewModel(): ActivitySkeletonSampleVModel {
        return ActivitySkeletonSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivitySkeletonSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, SkeletonSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}