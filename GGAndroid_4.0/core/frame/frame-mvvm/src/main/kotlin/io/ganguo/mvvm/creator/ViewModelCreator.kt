package io.ganguo.mvvm.creator

import io.ganguo.mvvm.viewmodel.ViewModel


/**
 * <pre>
 *     @author : leo
 *     time   : 2019/10/11
 *     desc   : ViewModel creator Interface
 * </pre>
 */
interface ViewModelCreator<B : ViewModel<*>> {
    val viewModel: B

    fun createViewModel(): B
}