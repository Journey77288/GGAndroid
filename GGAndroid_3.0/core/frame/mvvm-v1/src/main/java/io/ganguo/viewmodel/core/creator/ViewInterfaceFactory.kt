package io.ganguo.viewmodel.core.creator

import android.app.Dialog
import android.content.Context
import androidx.databinding.ViewDataBinding
import io.ganguo.adapter.hodler.ItemViewHolder
import io.ganguo.viewmodel.core.adapter.ViewModelAdapter
import io.ganguo.viewmodel.core.viewinterface.AdapterInterface
import io.ganguo.viewmodel.core.viewinterface.DialogInterface
import io.ganguo.viewmodel.core.viewinterface.ViewInterface


/**
 * <pre>
 *     @author : leo
 *     time   : 2019/10/11
 *     desc   : ViewInterface工厂,用于创建ViewInterface
 * </pre>
 */
object ViewInterfaceFactory {

    fun <V : ViewDataBinding> viewFactory(binding: V): ViewInterface<ViewDataBinding> {
        return object : ViewInterface<ViewDataBinding> {
            override val context: Context
                get() = binding.root.context

            override val binding: V
                get() = binding
        }
    }

    fun <V : ViewDataBinding> adapterViewFactory(adapter: ViewModelAdapter, vh: ItemViewHolder<V>): ViewInterface<V> {
        return object : AdapterInterface<V> {
            override val adapter: ViewModelAdapter
                get() = adapter

            override val viewHolder: ItemViewHolder<V>
                get() = vh

            override val context: Context
                get() = adapter.context

            override val binding: V
                get() = vh.binding
        }
    }

    fun <V : ViewDataBinding> dialogViewFactory(dialog: Dialog?, binding: V): DialogInterface<V> {
        if (dialog == null) {
            throw NullPointerException("dialog is null")
        }
        return object : DialogInterface<V> {
            override val dialog: Dialog
                get() = dialog

            override val context: Context
                get() = dialog.context

            override val binding: V
                get() = binding
        }
    }
}