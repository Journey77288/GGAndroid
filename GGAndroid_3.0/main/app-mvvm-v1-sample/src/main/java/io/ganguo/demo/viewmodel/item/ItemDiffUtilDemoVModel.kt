package io.ganguo.demo.viewmodel.item

import android.view.View

import io.ganguo.demo.R
import io.ganguo.demo.databinding.ItemDiffUtilDemoBinding
import io.ganguo.demo.entity.CommonDemoEntity
import io.ganguo.adapter.diffuitl.IComparator
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.viewinterface.AdapterInterface

/**
 *
 *
 * DiffUtil Demo Item ViewModel
 * 注：IDiffComparator<B> B传你想要获取比较的数据引用对象类型即可，可以是Entity，也可以是ViewModel，也可以是其他类型参数。
</B> *
 * Created by leo on 2018/8/27.
 */
class ItemDiffUtilDemoVModel(val entity: CommonDemoEntity) : BaseViewModel<AdapterInterface<ItemDiffUtilDemoBinding>>(), IComparator<CommonDemoEntity> {
    override val layoutId: Int by lazy {
        R.layout.item_diff_util_demo
    }
    override var itemData: CommonDemoEntity = entity
    var text: String? = null
    var removeAction: Function1<ItemDiffUtilDemoVModel, Unit>? = null

    init {
        this.text = entity.text
    }

    override fun itemEquals(t: CommonDemoEntity): Boolean {
        return entity==t
    }


    override fun onViewAttached(view: View) {

    }


    /**
     * 删除Item ViewModel
     *
     * @return
     */
    fun onRemoveClick(): View.OnClickListener {
        return View.OnClickListener {
            removeAction?.invoke(this)
        }
    }


}
