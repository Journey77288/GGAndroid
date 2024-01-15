package io.ganguo.viewmodel.core

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.ganguo.adapter.BR
import io.ganguo.adapter.hodler.ItemViewHolder
import io.ganguo.viewmodel.core.adapter.ViewModelAdapter
import io.ganguo.viewmodel.core.creator.ViewInterfaceFactory
import io.ganguo.viewmodel.core.viewinterface.ViewInterface

/**
 * <pre>
 *     @author : leo
 *     time   : 2020/05/29
 *     desc   : ViewModel辅助类，用于ViewModel与View绑定
 * </pre>
 */
object ViewModelHelper {


    /**
     * check ViewModel LayoutId
     * @param viewModel BaseViewModel<*>?
     * @return Boolean
     */
    @SuppressLint("ResourceType")
    private fun checkLayoutId(viewModel: BaseViewModel<*>?): Boolean {
        require(viewModel!!.layoutId > 0) { "viewModel not implement getItemLayoutId" }
        return true
    }

    /**
     * 用于与ViewInterface(View,Adapter,Activity)绑定
     * @param view ViewInterface<*>
     * @param parent BaseViewModel<*>?
     * @param viewModel B
     * @param variableId Int
     * @return B
     */
    fun <B : BaseViewModel<*>> bind(view: ViewInterface<*>, parent: BaseViewModel<*>?, viewModel: B, variableId: Int): B {
        checkLayoutId(viewModel)
        try {
            viewModel.attach(view, variableId)
        } catch (exception: ClassCastException) {
            Log.e("bind error:", "viewModel no match with layoutId or error viewInterface")
        }
        // 绑定生命周期
        parent?.let { bindLifecycle(it, viewModel) }
        return viewModel
    }

    fun <B : BaseViewModel<*>> bind(view: ViewInterface<*>, viewModel: B, variableId: Int): B {
        return bind(view, null, viewModel, variableId)
    }

    fun <B : BaseViewModel<*>> bind(view: ViewInterface<*>, parent: BaseViewModel<*>?, viewModel: B): B {
        return bind(view, parent, viewModel, BR.data)
    }

    fun <B : BaseViewModel<*>> bind(view: ViewInterface<*>, viewModel: B): B {
        return bind(view, null, viewModel, BR.data)
    }


    fun <V : ViewInterface<*>> bind(adapter: ViewModelAdapter, vh: ItemViewHolder<ViewDataBinding>, viewModel: BaseViewModel<V>): BaseViewModel<V> {
        return bind(ViewInterfaceFactory.adapterViewFactory(adapter, vh), adapter.parent, viewModel, BR.data)
    }

    /**
     * 绑定ViewModel到Dialog
     * @param dialog Dialog
     * @param viewModel B
     * @return B
     */
    fun <B : BaseViewModel<*>> bind(dialog: Dialog, viewModel: B): B {
        return bind(dialog, viewModel, BR.data)
    }

    fun <B : BaseViewModel<*>> bind(dialog: Dialog, viewModel: B, variableId: Int): B {
        checkLayoutId(viewModel)
        return bind(ViewInterfaceFactory.dialogViewFactory(dialog, inflate(dialog.context, viewModel.layoutId)), viewModel, variableId)
    }


    /**
     * 绑定ViewModel到ViewDataBinding
     * @param binding ViewDataBinding
     * @param parent BaseViewModel<*>?
     * @param viewModel B
     * @param variableId Int
     * @return B
     */
    fun <B : BaseViewModel<*>> bind(binding: ViewDataBinding, parent: BaseViewModel<*>?, viewModel: B, variableId: Int): B {
        checkLayoutId(viewModel)
        return bind(ViewInterfaceFactory.viewFactory(binding), parent, viewModel, variableId)
    }

    fun <B : BaseViewModel<*>> bind(binding: ViewDataBinding, viewModel: B, variableId: Int): B {
        checkLayoutId(viewModel)
        return bind(ViewInterfaceFactory.viewFactory(binding), null, viewModel, variableId)
    }

    fun <B : BaseViewModel<*>> bind(binding: ViewDataBinding, parent: BaseViewModel<*>?, viewModel: B): B {
        return bind(binding, parent, viewModel, BR.data)
    }

    fun <B : BaseViewModel<*>> bind(binding: ViewDataBinding, viewModel: B): B {
        return bind(binding, viewModel, BR.data)
    }

    /**
     * 绑定viewModel到view
     * @param view View
     * @param parent BaseViewModel<*>?
     * @param viewModel B
     * @param variableId Int
     * @return B
     */
    fun <B : BaseViewModel<*>> bind(view: View, parent: BaseViewModel<*>?, viewModel: B, variableId: Int): B {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(view.context), viewModel.layoutId, null, false)
        return bind(ViewInterfaceFactory.viewFactory(binding), parent, viewModel, variableId)
    }


    fun <B : BaseViewModel<*>> bind(view: View, viewModel: B, variableId: Int): B {
        return bind(view, null, viewModel, variableId)
    }

    fun <B : BaseViewModel<*>> bind(view: View, parent: BaseViewModel<*>?, viewModel: B): B {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(view.context), viewModel.layoutId, null, true)
        return bind(ViewInterfaceFactory.viewFactory(binding), parent, viewModel, BR.data)
    }

    fun <B : BaseViewModel<*>> bind(view: View, viewModel: B): B {
        return bind(view, null, viewModel)
    }

    /**
     * 绑定到ViewModel到ViewGroup
     * @param container ViewGroup
     * @param viewModel B
     * @return B
     */
    fun <B : BaseViewModel<*>> bind(container: ViewGroup, parent: BaseViewModel<*>?, viewModel: B, variableId: Int): B {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(container.context), viewModel.layoutId, null, false)
        container.addView(binding.root)
        return bind(ViewInterfaceFactory.viewFactory(binding), parent, viewModel, variableId)
    }


    fun <B : BaseViewModel<*>> bind(container: ViewGroup, viewModel: B, variableId: Int): B {
        return bind(container, null, viewModel, variableId)
    }

    fun <B : BaseViewModel<*>> bind(container: ViewGroup, parent: BaseViewModel<*>?, viewModel: B): B {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(container.context), viewModel.layoutId, container, true)
        return bind(ViewInterfaceFactory.viewFactory(binding), parent, viewModel, BR.data)
    }

    fun <B : BaseViewModel<*>> bind(container: ViewGroup, viewModel: B): B {
        return bind(container, null, viewModel)
    }

    /**
     * 将子ViewModel绑定父ViewModel生命周期
     * @param parent BaseViewModel<*>?
     * @param child BaseViewModel<*>?
     */
    private fun bindLifecycle(parent: BaseViewModel<*>?, child: BaseViewModel<*>?) {
        if (parent != null && child != null) {
            child.bindLifecycle(parent)
        } else {
            throw NullPointerException((if (parent == null) "parent" else "child") + "is null")
        }
    }

    /**
     * 绑定单个ViewModel
     * @param context Context
     * @param parent BaseViewModel<*>?
     * @param viewModel B
     * @return BaseViewModel<*>
     */
    fun <B : BaseViewModel<*>> bind(context: Context, parent: BaseViewModel<*>?, viewModel: B): BaseViewModel<*> {
        val viewDataBinding = inflate(context, viewModel.layoutId)
        return bind(viewDataBinding, parent, viewModel)
    }


    /**
     * 创建ViewDataBinding
     * @param context Context
     * @param layoutId Int
     * @return ViewDataBinding
     */
    fun inflate(context: Context, layoutId: Int): ViewDataBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, null, false)
    }

}