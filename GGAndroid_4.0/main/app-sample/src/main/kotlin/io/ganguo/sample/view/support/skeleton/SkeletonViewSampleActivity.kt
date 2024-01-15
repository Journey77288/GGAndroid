package io.ganguo.sample.view.support.skeleton

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.skeleton.ActivitySkeletonViewSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/04/28
 *     desc   : Skeleton With View Sample
 * </pre>
 */
class SkeletonViewSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivitySkeletonViewSampleVModel>() {
    override fun createViewModel(): ActivitySkeletonViewSampleVModel {
        return ActivitySkeletonViewSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivitySkeletonViewSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, SkeletonViewSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}