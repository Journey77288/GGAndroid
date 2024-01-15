package io.ganguo.mvvm.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.ganguo.mvvm.adapter.ViewModelAdapter
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewinterface.DialogInterface
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewinterface.WindowInterface
import io.ganguo.mvvm.core.creator.ViewInterfaceFactory
import io.support.recyclerview.BR
import io.support.recyclerview.adapter.hodler.ItemViewHolder

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
     * @param viewModel ViewModel<*>?
     * @return Boolean
     */
    @SuppressLint("ResourceType")
    private fun checkLayoutId(viewModel: ViewModel<*>?): Boolean {
        require(viewModel!!.layoutId > 0) { "viewModel not implement getItemLayoutId" }
        return true
    }

    /**
     * 用于与ViewInterface(View,Adapter,Activity)绑定
     * @param view ViewInterface<*>
     * @param parent ViewModel<*>?
     * @param viewModel B
     * @param variableId Int
     * @return B
     */
    fun <B : ViewModel<*>> bind(
            view: ViewInterface<*>,
            parent: ViewModel<*>?,
            viewModel: B,
            variableId: Int
    ): B {
        checkLayoutId(viewModel)
        try {
            // bind Lifecycle
            parent?.let {
                bindLifecycle(it, viewModel)
            }
            viewModel.attach(view, variableId)
        } catch (exception: ClassCastException) {
            Log.e("bind error:", "viewModel no match with layoutId or error viewInterface")
        }
        return viewModel
    }

    fun <B : ViewModel<*>> bind(view: ViewInterface<*>, viewModel: B, variableId: Int): B {
        return bind(view, null, viewModel, variableId)
    }

    fun <B : ViewModel<*>> bind(
            view: ViewInterface<*>,
            parent: ViewModel<*>?,
            viewModel: B
    ): B {
        return bind(view, parent, viewModel, BR.data)
    }


    fun <B : ViewModel<*>> bind(view: ActivityInterface<*>, viewModel: B): B {
        return bind(view, null, viewModel)
    }

    fun <B : ViewModel<*>> bind(view: ActivityInterface<*>, parent: ViewModel<*>?, viewModel: B): B {
        return bind(view, parent, viewModel, BR.data)
    }

    fun <B : ViewModel<*>> bind(view: DialogInterface<*>, parent: ViewModel<*>?, viewModel: B): B {
        return bind(view, parent, viewModel, BR.data)
    }

    fun <B : ViewModel<*>> bind(view: DialogInterface<*>, viewModel: B): B {
        return bind(view, null, viewModel)
    }

    fun <B : ViewModel<*>> bind(view: WindowInterface<*>, parent: ViewModel<*>?, viewModel: B): B {
        return bind(view, parent, viewModel, BR.data)
    }

    fun <B : ViewModel<*>> bind(view: WindowInterface<*>, viewModel: B): B {
        return bind(view, null, viewModel)
    }


    fun <B : ViewModel<*>> bind(view: ViewInterface<*>, viewModel: B): B {
        return bind(view, null, viewModel, BR.data)
    }


    fun <B : ViewModel<*>> bind(
            adapter: ViewModelAdapter,
            vh: ItemViewHolder<ViewDataBinding>,
            viewModel: B
    ): B {
        return bind(
                ViewInterfaceFactory.adapterViewFactory(adapter, vh),
                adapter.parent,
                viewModel,
                BR.data
        )
    }


    /**
     * 绑定ViewModel到ViewDataBinding
     * @param binding ViewDataBinding
     * @param parent ViewModel<*>?
     * @param viewModel B
     * @param variableId Int
     * @return B
     */
    fun <B : ViewModel<*>> bind(
            binding: ViewDataBinding,
            parent: ViewModel<*>?,
            viewModel: B,
            variableId: Int
    ): B {
        checkLayoutId(viewModel)
        return bind(ViewInterfaceFactory.viewFactory(binding), parent, viewModel, variableId)
    }

    fun <B : ViewModel<*>> bind(binding: ViewDataBinding, viewModel: B, variableId: Int): B {
        checkLayoutId(viewModel)
        return bind(ViewInterfaceFactory.viewFactory(binding), null, viewModel, variableId)
    }

    fun <B : ViewModel<*>> bind(
            binding: ViewDataBinding,
            parent: ViewModel<*>?,
            viewModel: B
    ): B {
        return bind(binding, parent, viewModel, BR.data)
    }

    fun <B : ViewModel<*>> bind(binding: ViewDataBinding, viewModel: B): B {
        return bind(binding, viewModel, BR.data)
    }

    /**
     * 绑定viewModel到view
     * @param view View
     * @param parent ViewModel<*>?
     * @param viewModel B
     * @param variableId Int
     * @return B
     */
    fun <B : ViewModel<*>> bind(
            view: View,
            parent: ViewModel<*>?,
            viewModel: B,
            variableId: Int
    ): B {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(view.context), viewModel.layoutId, null, false
        )
        return bind(ViewInterfaceFactory.viewFactory(binding), parent, viewModel, variableId)
    }


    fun <B : ViewModel<*>> bind(view: View, viewModel: B, variableId: Int): B {
        return bind(view, null, viewModel, variableId)
    }

    fun <B : ViewModel<*>> bind(view: View, parent: ViewModel<*>?, viewModel: B): B {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(view.context),
                viewModel.layoutId, null, true
        )
        return bind(ViewInterfaceFactory.viewFactory(binding), parent, viewModel, BR.data)
    }

    fun <B : ViewModel<*>> bind(view: View, viewModel: B): B {
        return bind(view, null, viewModel)
    }

    /**
     * 绑定到ViewModel到ViewGroup
     * @param container ViewGroup
     * @param viewModel B
     * @return B
     */
    fun <B : ViewModel<*>> bind(
            container: ViewGroup,
            parent: ViewModel<*>?,
            viewModel: B,
            variableId: Int
    ): B {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(container.context), viewModel.layoutId, null, false
        )
        container.addView(binding.root)
        return bind(ViewInterfaceFactory.viewFactory(binding), parent, viewModel, variableId)
    }


    fun <B : ViewModel<*>> bind(container: ViewGroup, viewModel: B, variableId: Int): B {
        return bind(container, null, viewModel, variableId)
    }

    fun <B : ViewModel<*>> bind(
            container: ViewGroup,
            parent: ViewModel<*>?,
            viewModel: B
    ): B {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(container.context), viewModel.layoutId,
                container, true
        )
        return bind(ViewInterfaceFactory.viewFactory(binding), parent, viewModel, BR.data)
    }

    fun <B : ViewModel<*>> bind(container: ViewGroup, viewModel: B): B {
        return bind(container, null, viewModel)
    }

    /**
     * 将子ViewModel绑定父ViewModel生命周期
     * @param parent ViewModel<*>?
     * @param child ViewModel<*>?
     */
    private fun bindLifecycle(parent: ViewModel<*>?, child: ViewModel<*>?) {
        if (parent != null && child != null) {
            child.bindLifecycle(parent)

        } else {
            throw NullPointerException((if (parent == null) "parent" else "child") + "is null")
        }
    }

    /**
     * 绑定单个ViewModel
     * @param context Context
     * @param parent ViewModel<*>?
     * @param viewModel B
     * @return ViewModel<*>
     */
    fun <B : ViewModel<*>> bind(
            context: Context,
            parent: ViewModel<*>?,
            viewModel: B
    ): B {
        val viewDataBinding = inflate(context, viewModel.layoutId) as ViewDataBinding
        return bind(viewDataBinding, parent, viewModel)
    }

    /**
     * 绑定单个ViewModel
     * @param context Context
     * @param viewModel B
     * @return ViewModel<*>
     */
    fun <B : ViewModel<*>> bind(
            context: Context,
            viewModel: B
    ): B {
        val viewDataBinding: ViewDataBinding = inflate(context, viewModel.layoutId)
        return bind(viewDataBinding, null, viewModel)
    }


    /**
     * 创建ViewDataBinding
     * @param context Context
     * @param layoutId Int
     * @return ViewDataBinding
     */
    fun <T : ViewDataBinding> inflate(context: Context, layoutId: Int): T {
        return DataBindingUtil.inflate<T>(LayoutInflater.from(context), layoutId, null, false)
    }

}
