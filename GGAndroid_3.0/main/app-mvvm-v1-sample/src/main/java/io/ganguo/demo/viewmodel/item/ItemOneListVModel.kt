package io.ganguo.demo.viewmodel.item

import android.view.View

import androidx.databinding.ObservableField
import io.ganguo.demo.R
import io.ganguo.demo.databinding.ItemOneListBinding
import io.ganguo.demo.entity.one.MovieEntity
import io.ganguo.adapter.diffuitl.IComparator
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.core.BaseViewModel

/**
 *
 *
 * 《一个》 - 数据ViewModel
 *
 * Created by leo on 2018/7/30.
 */

class ItemOneListVModel(private val oneEntity: MovieEntity) : BaseViewModel<ViewInterface<ItemOneListBinding>>(), IComparator<ItemOneListVModel> {
    override val layoutId: Int by lazy { R.layout.item_one_list }
    override var itemData: ItemOneListVModel = this
    var imageUrl = ObservableField<String>()
    var content = ObservableField<String>()

    init {
        this.imageUrl.set(oneEntity.images!!.medium)
        this.content.set(oneEntity.originalTitle)
    }


    override fun onViewAttached(view: View) {

    }

    override fun itemEquals(t: ItemOneListVModel): Boolean {
        return oneEntity == t.oneEntity
    }

}
