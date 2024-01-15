package io.ganguo.state.core

import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import io.ganguo.log.core.Logger
import io.ganguo.state.Constants
import io.ganguo.state.IStateView

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/04
 *     desc   : Page state layout management
 * </pre>
 */
open class StateViewHelper(private var stateLayout: ViewGroup) : IStateView {
    private var stateCreators: HashMap<String, IStateViewCreator> = HashMap()
    private var stateViewMap: HashMap<String, View> = HashMap()

    /**
     * add IStateViewCreator
     * @param stateViewKey String
     * @param creator IStateViewCreator
     */
    fun addCreator(stateViewKey: String, creator: IStateViewCreator) {
        stateCreators[stateViewKey] = creator
    }


    /**
     * Displays the state layout corresponding to the [stateViewKey]
     * @param stateViewKey String
     */
    fun showStateView(stateViewKey: String) {
        if (!stateCreators.containsKey(stateViewKey)) {
            Logger.e("Please add the IStateViewCreator corresponding to $stateViewKey")
            return
        }
        var view = getStateView(stateViewKey)
        cacheStateView(stateViewKey, view)
        switchStateViewVisible(stateViewKey)
        showStateLayout()
    }

    /**
     * Displays the state layout corresponding to the [stateViewKey]
     * @param stateViewKey String
     */
    fun hideStateView(stateViewKey: String) {
        if (!stateViewMap.containsKey(stateViewKey)) {
            Logger.e("Please add the StateView corresponding to $stateViewKey")
            return
        }
        var view = stateViewMap[stateViewKey]
        view?.visibility = View.GONE
    }

    /**
     * show Empty state view
     */
    override fun showEmptyView() {
        showStateView(Constants.EMPTY)
    }

    /**
     * show Error state view
     */
    override fun showErrorView() {
        showStateView(Constants.ERROR)
    }

    /**
     * show netWork state view
     */
    override fun showNetWorkErrorView() {
        showStateView(Constants.NETWORK_ERROR)
    }

    /**
     * show Loading state view
     */
    override fun showLoadingView() {
        showStateView(Constants.LOADING)
    }

    /**
     * show state layout
     */
    override fun showStateLayout() {
        if (stateLayout.visibility != View.VISIBLE) {
            stateLayout.visibility = View.VISIBLE
        }
    }


    /**
     * hide state layout
     */
    override fun hideStateLayout() {
        if (stateLayout.visibility != View.GONE) {
            stateLayout.visibility = View.GONE
        }
    }


    /**
     * get state view
     * @param stateViewKey String
     * @return View
     */
    @NonNull
    private fun getStateView(stateViewKey: String): View? {
        var stateView = stateViewMap[stateViewKey]
        if (stateView == null) {
            var creator = stateCreators[stateViewKey]
            stateView = creator!!.attachStateView(stateLayout)
            stateView?.visibility = View.VISIBLE
        }
        return stateView
    }

    /**
     * Cache [stateViewKey] type views
     * @param stateViewKey String
     * @param stateView View
     */
    @Synchronized
    private fun cacheStateView(stateViewKey: String, stateView: View?) {
        if (!stateViewMap.containsKey(stateViewKey) && stateView != null) {
            stateViewMap[stateViewKey] = stateView
        }
    }

    /**
     * Display [stateViewKey] corresponding status View, and hide the rest
     * @param stateViewKey String
     */
    private fun switchStateViewVisible(stateViewKey: String) {
        stateViewMap.forEach {
            if (it.key == stateViewKey) {
                it.value.visibility = View.VISIBLE
            } else {
                it.value.visibility = View.GONE
            }
        }
    }

}
