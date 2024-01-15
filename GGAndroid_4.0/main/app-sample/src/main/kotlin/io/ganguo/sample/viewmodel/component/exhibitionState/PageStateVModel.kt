package io.ganguo.sample.viewmodel.component.exhibitionState

import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.R
import io.ganguo.sample.bean.ExhibitionState
import io.ganguo.sample.databinding.ComponentStatePageBinding
import io.ganguo.state.core.StateViewService
import io.support.recyclerview.adapter.diffuitl.IDiffComparator

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/19
 *     desc   : Page Empty ViewModel
 * </pre>
 */
class PageStateVModel(private val state: ExhibitionState, private val parent: ViewModel<*>) :
        ViewModel<ViewInterface<ComponentStatePageBinding>>(), StateViewService, IDiffComparator<ExhibitionState> {
    override val layoutId: Int = R.layout.component_state_page
    val hint = ObservableField<String>()

    init {
        initData()
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        val hintRes = when (state) {
            ExhibitionState.EMPTY -> R.string.str_empty_hint
            ExhibitionState.ERROR -> R.string.str_error_hint
            ExhibitionState.NETWORK_ERROR -> R.string.str_network_error
            else -> 0
        }
        if (hintRes > 0) {
            hint.set(getString(hintRes))
        }
    }

    override fun onViewAttached(view: View) {

    }

    override fun createStateView(context: Context): View = let {
        ViewModelHelper.bind(context, parent, this).rootView
    }

    override fun itemEquals(t: ExhibitionState): Boolean {
        return state == t
    }

    override fun getItem(): ExhibitionState {
        return state
    }
}