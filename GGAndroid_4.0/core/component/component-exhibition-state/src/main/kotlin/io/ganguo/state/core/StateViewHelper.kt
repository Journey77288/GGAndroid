package io.ganguo.state.core

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import io.ganguo.state.Constants
import java.util.concurrent.ConcurrentHashMap

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/04
 *     desc   : Page state layout management
 * </pre>
 */
open class StateViewHelper : IStateViewHelper {
    private var stateCreators: ConcurrentHashMap<String, StateViewService> = ConcurrentHashMap()
    private var stateViewMap: ConcurrentHashMap<String, View> = ConcurrentHashMap()
    private lateinit var stateLayout: ViewGroup

    /**
     * bind state ViewGroup
     * @param stateLayout ViewGroup
     */
    override fun bindStateLayout(stateLayout: ViewGroup?) {
        stateLayout?.let {
            this.stateLayout = stateLayout
            this.stateLayout.visibility = View.GONE
        }
    }

    /**
     * add StateViewService
     * @param stateViewKey String
     * @param service StateViewService
     */
    override fun addCreator(stateViewKey: String, service: StateViewService) {
        stateCreators[stateViewKey] = service
    }


    /**
     * add Loading StateViewService
     * @param service StateViewService
     */
    override fun addLoadingViewCreator(service: StateViewService) {
        stateCreators[Constants.LOADING] = service
    }

    /**
     * add Empty StateViewService
     * @param service StateViewService
     */
    override fun addEmptyViewCreator(service: StateViewService) {
        stateCreators[Constants.EMPTY] = service
    }


    /**
     * add Error View StateViewService
     * @param service StateViewService
     */
    override fun addErrorViewCreator(service: StateViewService) {
        stateCreators[Constants.ERROR] = service
    }


    /**
     * add Network Error StateViewService
     * @param service StateViewService
     */
    override fun addNetworkErrorViewCreator(service: StateViewService) {
        stateCreators[Constants.NETWORK_ERROR] = service
    }


    /**
     * Displays the state layout corresponding to the [stateViewKey]
     * @param stateViewKey String
     */
    override fun showStateView(stateViewKey: String) {
        if (!stateCreators.containsKey(stateViewKey)) {
            Log.e("StateViewHelper", "Please add the StateViewService corresponding to $stateViewKey")
            return
        }
        var view = getStateView(stateViewKey)
        cacheStateView(stateViewKey, view)
        showStateViewVisible(stateViewKey)
        showStateLayout()
    }

    /**
     * Displays the [stateViewKey] corresponding View and sends a display callback
     * @param stateViewKey String
     * @param view View?
     */
    private fun showStateView(stateViewKey: String, view: View?) {
        if (!stateViewMap.containsKey(stateViewKey)) {
            Log.e("StateViewHelper", "Please add the StateView corresponding to $stateViewKey")
            return
        }
        if (view?.visibility != View.VISIBLE) {
            view?.visibility = View.VISIBLE
            stateCreators[stateViewKey]?.onStateViewVisibleChanged(true)
        }
    }

    /**
     * Hide [stateViewKey] for the View and send the display callback
     * @param stateViewKey String
     */
    override fun hideStateView(stateViewKey: String) {
        if (!stateViewMap.containsKey(stateViewKey)) {
            Log.e("StateViewHelper", "Please add the StateView corresponding to $stateViewKey")
            return
        }
        var view = stateViewMap[stateViewKey]
        if (view?.visibility != View.GONE) {
            view?.visibility = View.GONE
            stateCreators[stateViewKey]?.onStateViewVisibleChanged(false)
        }
    }


    /**
     * Hide all status views
     */
    private fun hideAllStateView() {
        stateViewMap.forEach {
            if (it.value.visibility != View.GONE) {
                hideStateView(it.key)
            }
        }
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
        hideAllStateView()
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
            stateView = creator?.createStateView(stateLayout.context)
            stateView?.visibility = View.GONE
            stateLayout.addView(stateView)
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
    private fun showStateViewVisible(stateViewKey: String) {
        stateViewMap.forEach {
            if (it.key == stateViewKey) {
                showStateView(stateViewKey, it.value)
            } else if (it.value.visibility != View.GONE) {
                it.value.visibility = View.GONE
            }
        }
    }

}
