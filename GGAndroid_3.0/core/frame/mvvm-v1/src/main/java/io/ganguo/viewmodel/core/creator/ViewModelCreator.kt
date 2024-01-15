package io.ganguo.viewmodel.core.creator

import io.ganguo.viewmodel.core.BaseViewModel


/**
 * <pre>
 *     @author : leo
 *     time   : 2019/10/11
 *     desc   : ViewModel Creator Interface
 * </pre>
 */
interface ViewModelCreator<B : BaseViewModel<*>> {
    val viewModel: B

    fun createViewModel(): B
}