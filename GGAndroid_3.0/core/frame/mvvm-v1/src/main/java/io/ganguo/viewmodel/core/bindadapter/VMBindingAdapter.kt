@file:JvmName("ViewModelBindingAdapter")

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/29
 *     desc   : ViewModel DataBinding Adapter
 * </pre>
 */
package io.ganguo.viewmodel.core.bindadapter

import android.os.Build
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.databinding.BindingAdapter
import io.ganguo.core.R
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.core.viewinterface.ViewInterface

/**
 * binding ViewModel List to ViewGroup
 * @receiver ViewGroup
 * @param viewModelList Collection<BaseViewModel<*>?>
 */
@BindingAdapter("android:bind_view_model_list_to_layout")
fun ViewGroup.bindViewModelList(viewModelList: Collection<BaseViewModel<*>>) {
    viewModelList
            .filter {
                !it.isAttach
            }
            .forEach {
                ViewModelHelper.bind(this, it)
            }
}

/**
 * binding ViewModel List to ViewGroup
 * @param viewModel
 */
@BindingAdapter("android:bind_view_model_to_layout")
fun ViewGroup.bindViewModel(viewModel: BaseViewModel<*>) {
    if (!viewModel.isAttach) {
        ViewModelHelper.bind(this, viewModel)
    }
}

/**
 * Setting ViewGroup Elevation Height
 * @receiver ViewGroup
 * @param isEnable Boolean
 */
@BindingAdapter("android:bind_enable_elevation_to_layout")
fun ViewGroup.bindEnableElevation(isEnable: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        outlineProvider = if (isEnable) {
            ViewOutlineProvider.BOUNDS
        } else {
            ViewOutlineProvider.BACKGROUND
        }
        elevation = if (isEnable) {
            resources.getDimension(R.dimen.dp_4)
        } else {
            0f
        }
    }
}