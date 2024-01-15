package io.ganguo.sample.viewmodel.support.skeleton

import android.view.View
import androidx.databinding.ObservableField
import io.ganguo.mvvm.viewinterface.AdapterInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.sample.R
import io.ganguo.sample.databinding.ItemSkeletonBannerBinding
import io.ganguo.sample.databinding.ItemSkeletonContentBinding

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/04/28
 *     desc   : Skeleton View Content
 * </pre>
 */
class ItemSkeletonContentVModel : ViewModel<AdapterInterface<ItemSkeletonContentBinding>>() {
    override val layoutId: Int = R.layout.item_skeleton_content
    val cover = ObservableField<String>()

    init {
        cover.set("https://www.natureimagesawards.com/wp-content/uploads/2019/05/archway-in-a-pod.jpg")
    }

    override fun onViewAttached(view: View) {

    }
}