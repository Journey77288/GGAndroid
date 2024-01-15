package io.ganguo.core.http

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.databinding.ViewStubProxy

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/14
 *   @desc   : ViewStub inflate layout Handler
 * </pre>
 */
interface ViewStubHandler {

    /**
     * Apply colours to a drawing layout
     * @param stubProxy ViewStubProxy
     * @return ViewDataBinding?
     */
    fun inflateToViewBinding(stubProxy: ViewStubProxy): ViewDataBinding?

    /**
     * Apply colours to a drawing layout
     * @param stubProxy ViewStubProxy
     * @return ViewDataBinding?
     */
    fun inflateToView(stubProxy: ViewStubProxy): View?

    /**
     * Apply colours to a drawing layout
     * @param stubProxy ViewStubProxy
     * @return ViewDataBinding?
     */
    fun inflateToViewBinding(stubProxy: ViewStubProxy, layoutId: Int): ViewDataBinding?


    /**
     * Apply colours to a drawing layout
     * @param stubProxy ViewStubProxy
     * @return ViewDataBinding?
     */
    fun inflateToView(stubProxy: ViewStubProxy, layoutId: Int): View?


    /**
     * Check to set the layout ID
     * @param layoutId Int
     */
    fun checkLayoutId(@IdRes layoutId: Int)


    /**
     * create ViewGroup from ViewStubProxy
     * @param layoutId Int
     * @param stub ViewStubProxy
     * @return ViewGroup
     */
    fun createViewGroup(layoutId: Int, stub: ViewStubProxy): ViewGroup = let {
        var binding = inflateToViewBinding(stub, layoutId)
        binding?.root as ViewGroup
    }
}
