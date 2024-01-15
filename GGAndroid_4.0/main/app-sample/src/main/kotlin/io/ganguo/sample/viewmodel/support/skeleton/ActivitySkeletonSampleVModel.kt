package io.ganguo.sample.viewmodel.support.skeleton

import android.view.View
import android.view.ViewGroup
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.R
import io.ganguo.sample.view.support.skeleton.SkeletonRecyclerViewActivity
import io.ganguo.sample.view.support.skeleton.SkeletonViewSampleActivity
import io.ganguo.sample.viewmodel.ItemMenuVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/04/28
 *     desc   : Skeleton Sample
 * </pre>
 */
class ActivitySkeletonSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {

    /**
     * 设置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_skeleton_sample))
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    override fun onViewAttached(view: View) {
        with(adapter) {
            addAll(
                listOf(
                    ItemMenuVModel.create(getString(R.string.str_skeleton_with_view)) {
                        SkeletonViewSampleActivity.start(context)
                    },
                    ItemMenuVModel.create(getString(R.string.str_skeleton_with_recyclerview)) {
                        SkeletonRecyclerViewActivity.start(context)
                    }
                )
            )
        }
    }
}