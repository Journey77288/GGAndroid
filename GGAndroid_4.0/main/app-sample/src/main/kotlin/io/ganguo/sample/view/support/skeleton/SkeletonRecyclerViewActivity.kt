package io.ganguo.sample.view.support.skeleton

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.skeleton.ActivitySkeletonRecyclerViewVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/04/28
 *     desc   : Skeleton with RecyclerView Sample
 * </pre>
 */
class SkeletonRecyclerViewActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivitySkeletonRecyclerViewVModel>() {
    override fun createViewModel(): ActivitySkeletonRecyclerViewVModel {
        return ActivitySkeletonRecyclerViewVModel()
    }

    override fun onViewAttached(viewModel: ActivitySkeletonRecyclerViewVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, SkeletonRecyclerViewActivity::class.java)
            context.startActivity(intent)
        }
    }
}