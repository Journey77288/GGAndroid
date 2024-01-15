package io.ganguo.viewmodel.pack.helper.state

import android.view.View
import android.view.ViewGroup
import io.ganguo.state.core.IStateViewCreator
import io.ganguo.state.core.StateViewHelper
import io.ganguo.utils.helper.ResHelper
import io.ganguo.viewmodel.R
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.pack.common.PageStateVModel
import io.ganguo.viewmodel.pack.common.loading.LoadingMaterialProgressVModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/14
 *   @desc   : StateViewCreator Factory
 * </pre>
 */
object StateViewCreatorFactory {


    /**
     * 空数据提示
     * @param parent BaseViewModel<*>
     * @return IStateViewCreator
     */
    fun emptyViewCreator(parent: BaseViewModel<*>): IStateViewCreator {
        return object : IStateViewCreator {
            override fun attachStateView(stateLayout: ViewGroup): View? {
                return ViewModelHelper.bind(stateLayout, parent, PageStateVModel(ResHelper.getString(R.string.str_empty))).let {
                    it.rootView
                }
            }
        }
    }


    /**
     * 加载loading
     * @param parent BaseViewModel<*>
     * @return IStateViewCreator
     */
    fun loadingViewCreator(parent: BaseViewModel<*>): IStateViewCreator {
        return object : IStateViewCreator {
            override fun attachStateView(stateLayout: ViewGroup): View? {
                return ViewModelHelper.bind(stateLayout, parent, LoadingMaterialProgressVModel()).let {
                    it.rootView
                }
            }
        }
    }


    /**
     * 网络错误
     * @param parent BaseViewModel<*>
     * @return IStateViewCreator
     */
    fun netWorkErrorViewCreator(parent: BaseViewModel<*>): IStateViewCreator {
        return object : IStateViewCreator {
            override fun attachStateView(stateLayout: ViewGroup): View? {
                return ViewModelHelper.bind(stateLayout, parent, PageStateVModel(ResHelper.getString(R.string.str_network_error))).let {
                    it.rootView
                }
            }
        }
    }

    /**
     * 常见错误
     * @param parent BaseViewModel<*>
     * @return IStateViewCreator
     */
    fun errorViewCreator(parent: BaseViewModel<*>): IStateViewCreator {
        return object : IStateViewCreator {
            override fun attachStateView(stateLayout: ViewGroup): View? {
                return ViewModelHelper.bind(stateLayout, parent, PageStateVModel(ResHelper.getString(R.string.str_error))).let {
                    it.rootView
                }
            }
        }
    }

}
