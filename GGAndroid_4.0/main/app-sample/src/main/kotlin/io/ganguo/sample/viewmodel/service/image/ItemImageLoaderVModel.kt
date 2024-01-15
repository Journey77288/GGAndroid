package io.ganguo.sample.viewmodel.service.image

import android.view.View
import androidx.annotation.Dimension
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.sample.databinding.ItemImageLoaderBinding
import io.support.recyclerview.adapter.diffuitl.IDiffComparator

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/10/13
 *   @desc   : 图片加载Demo - Item VieModel
 * </pre>
 */
class ItemImageLoaderVModel(val imageUrl: String)
    : ViewModel<ViewInterface<ItemImageLoaderBinding>>(), IDiffComparator<Any> {
    override val layoutId: Int = io.ganguo.sample.R.layout.item_image_loader
    override fun onViewAttached(view: View) {
    }

    override fun itemEquals(t: Any): Boolean {
        return getItem() == t
    }

    override fun getItem(): Any {
        return imageUrl
    }

}
