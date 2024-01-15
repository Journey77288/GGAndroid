package io.ganguo.mvvm.core.creator

import android.app.Dialog
import android.content.Context
import androidx.databinding.ViewDataBinding
import io.support.recyclerview.adapter.hodler.ItemViewHolder
import io.ganguo.mvvm.adapter.ViewModelAdapter
import io.ganguo.mvvm.viewinterface.AdapterInterface
import io.ganguo.mvvm.viewinterface.DialogInterface
import io.ganguo.mvvm.viewinterface.ViewInterface


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
            override val viewContext: Context
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

            override val viewContext: Context
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

            override val viewContext: Context
                get() = dialog.context

            override val binding: V
                get() = binding
        }
    }
}
