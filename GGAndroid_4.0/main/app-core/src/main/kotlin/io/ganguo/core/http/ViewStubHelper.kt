@file:Suppress("KDocUnresolvedReference")

package io.ganguo.core.http

import android.view.View
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.ViewStubProxy

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/14
 *   @desc   : ViewStub inflate layout Handler
 * </pre>
 * 注意事项:
 *  1、ViewStub对应的layout根布局高度、margin会失效，边距建议使用padding属性
 */
@Suppress("KDocUnresolvedReference")
object ViewStubHelper : ViewStubHandler {

    /**
     * Apply colours to a drawing layout
     * @param stubProxy ViewStubProxy
     * @return ViewDataBinding?
     */
    override fun inflateToViewBinding(stubProxy: ViewStubProxy): ViewDataBinding? {
        return if (stubProxy.viewStub != null) {
            val layoutId = stubProxy.viewStub?.layoutResource ?: 0
            inflateToViewBinding(stubProxy, layoutId)
        } else {
            stubProxy.binding
        }
    }

    /**
     * Apply colours to a drawing layout
     * @param stubProxy ViewStubProxy
     * @return ViewDataBinding?
     */
    override fun inflateToView(stubProxy: ViewStubProxy): View? {
        return if (stubProxy.viewStub != null) {
            val layoutId = stubProxy.viewStub?.layoutResource ?: 0
            inflateToView(stubProxy, layoutId)
        } else {
            stubProxy.binding?.root
        }
    }

    /**
     * Apply colours to a drawing layout
     * @param stubProxy ViewStubProxy
     * @return ViewDataBinding?
     */
    override fun inflateToViewBinding(stubProxy: ViewStubProxy, layoutId: Int): ViewDataBinding? {
        val view = inflateToView(stubProxy, layoutId)
        return if (view != null) {
            DataBindingUtil.findBinding(view)
        } else {
            null
        }
    }

    /**
     * Apply colours to a drawing layout
     * @param stubProxy ViewStubProxy
     * @return ViewDataBinding?
     */
    override fun inflateToView(stubProxy: ViewStubProxy, layoutId: Int): View? {
        val isInflated = stubProxy.isInflated
        val stub = stubProxy.viewStub
        return (if (!isInflated && stub != null) {
            stub.layoutResource = layoutId
            stub.inflate()
        } else {
            stubProxy.root
        })
    }


    /**
     * Check to set the layout ID
     * @param layoutId Int
     */
    override fun checkLayoutId(@IdRes layoutId: Int) {
        check(layoutId != 0) {
            "The layout file id cannot be 0"
        }
    }
}
