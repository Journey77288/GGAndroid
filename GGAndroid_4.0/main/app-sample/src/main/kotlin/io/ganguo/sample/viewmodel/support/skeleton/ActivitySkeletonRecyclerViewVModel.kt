package io.ganguo.sample.viewmodel.support.skeleton

import android.view.View
import android.view.ViewGroup
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.R
import io.ganguo.utils.postDelayed
import io.ganguo.utils.view.SkeletonHelper

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/04/28
 *     desc   : Skeleton with RecyclerView Sample
 * </pre>
 */
class ActivitySkeletonRecyclerViewVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {
    private val skeleton: RecyclerViewSkeletonScreen by lazy {
        SkeletonHelper.createSkeletonForRecyclerView(recyclerView, R.layout.item_content_skeleton)
    }

    /**
     * 设置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_skeleton_with_recyclerview))
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    override fun onViewAttached(view: View) {
        skeleton.show()
        postDelayed(2000) {
            with(adapter) {
                val list = mutableListOf<ItemSkeletonContentVModel>()
                for (i in 1..4) {
                    list.add(ItemSkeletonContentVModel())
                }
                addAll(list)
                skeleton.hide()
            }
        }
    }
}