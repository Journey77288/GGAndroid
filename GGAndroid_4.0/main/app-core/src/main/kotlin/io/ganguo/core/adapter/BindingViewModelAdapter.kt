@file:JvmName("ViewModelBindingAdapter")

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/29
 *     desc   : ViewModel DataBinding Adapter
 * </pre>
 */
package io.ganguo.core.adapter

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper

/**
 * binding ViewModel List to ViewGroup
 * @receiver ViewGroup
 * @param viewModelList Collection<ViewModel<*>?>
 */
@BindingAdapter(value = [
    "android:bind_view_models_to_layout",
    "android:bind_view_model_parent"
])
fun ViewGroup.bindViewModelList(viewModelList: Collection<ViewModel<*>>, parentVModel: ViewModel<*>? = null) {
	viewModelList
		  .filter {
			  !it.isAttach()
		  }
		  .forEach {
			  ViewModelHelper.bind(this, parentVModel, it)
		  }
}

/**
 * binding ViewModel List to ViewGroup
 * @param viewModel
 */
@BindingAdapter(value = [
    "android:bind_view_model_to_layout",
    "android:bind_view_model_parent"
])
fun ViewGroup.bindViewModel(viewModel: ViewModel<*>, parentVModel: ViewModel<*>? = null) {
	if (!viewModel.isAttach()) {
		ViewModelHelper.bind(this, parentVModel, viewModel)
	}
}
