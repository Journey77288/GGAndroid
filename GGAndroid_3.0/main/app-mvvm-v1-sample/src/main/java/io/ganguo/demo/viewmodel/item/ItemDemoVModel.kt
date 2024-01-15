package io.ganguo.demo.viewmodel.item


import android.view.View
import io.ganguo.demo.R
import io.ganguo.demo.databinding.ItemDemoBinding
import io.ganguo.adapter.diffuitl.IComparator
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.core.BaseViewModel


/**
 * Created by Roger on 6/4/16.
 */
class ItemDemoVModel<T> : BaseViewModel<ViewInterface<ItemDemoBinding>>(), IComparator<T> {
    var data: T? = null
    override val layoutId: Int by lazy { R.layout.item_demo }
    override val itemData: T by lazy { data!! }
    private var content: String? = null
    private var btnText: String? = null
    private var clickAction: Function1<ItemDemoVModel<T>, Unit>? = null

    override fun onViewAttached(view: View) {

    }


    fun onClick(): View.OnClickListener {
        return View.OnClickListener {
            if (clickAction != null) {
                clickAction!!.invoke(this@ItemDemoVModel)
            }
        }
    }


    override fun itemEquals(t: T): Boolean {
        return data?.equals(t)!!
    }


    fun getContent(): String? {
        return content
    }

    fun setContent(content: String): ItemDemoVModel<T> {
        this.content = content
        return this
    }

    fun getBtnText(): String? {
        return btnText
    }

    fun setBtnText(btnText: String): ItemDemoVModel<T> {
        this.btnText = btnText
        return this
    }

    fun getClickAction(): Function1<ItemDemoVModel<T>, Unit>? {
        return clickAction
    }

    fun setClickAction(clickAction: Function1<ItemDemoVModel<T>, Unit>): ItemDemoVModel<T> {
        this.clickAction = clickAction
        return this
    }


    fun setDataObj(data: T): ItemDemoVModel<T> {
        this.data = data
        return this
    }


}
