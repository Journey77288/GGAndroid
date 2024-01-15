package io.ganguo.demo.viewmodel.activity

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import io.ganguo.demo.R
import io.ganguo.demo.databinding.ActivityPageStateBinding
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.pack.base.viewmodel.BaseSupportStateViewModel

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/09
 *     desc   : 页面状态ViewModel
 * </pre>
 */
class PageStateDemoVModel : BaseSupportStateViewModel<ViewInterface<ActivityPageStateBinding>>() {
    var errorSelect = ObservableBoolean(false)
    var contentSelect = ObservableBoolean(false)
    var emptySelect = ObservableBoolean(false)
    var netWorkErrorSelect = ObservableBoolean(false)
    var loadingSelect = ObservableBoolean(true)

    override val stateLayout: ViewGroup by lazy {
        viewInterface.binding.flyState
    }

    override fun onViewAttached(view: View) {
        showLoadingView()
    }


    /**
     * 显示页面Loading
     * @return View.OnClickListener
     */
    fun actionShowLoading(): View.OnClickListener {
        return View.OnClickListener {
            showLoadingView()
            changeState(loadingSelect)
        }
    }

    /**
     * 显示页面错误
     * @return View.OnClickListener
     */
    fun actionShowPageError(): View.OnClickListener {
        return View.OnClickListener {
            showErrorView()
            changeState(errorSelect)
        }
    }

    /**
     * 显示页面错误
     * @return View.OnClickListener
     */
    fun actionShowNetWorkError(): View.OnClickListener {
        return View.OnClickListener {
            showNetWorkErrorView()
            changeState(netWorkErrorSelect)
        }
    }

    /**
     * 显示空数据页面
     * @return View.OnClickListener
     */
    fun actionShowEmpty(): View.OnClickListener {
        return View.OnClickListener {
            showEmptyView()
            changeState(emptySelect)
        }
    }

    /**
     * 显示页面内容
     * @return View.OnClickListener
     */
    fun actionShowContent(): View.OnClickListener {
        return View.OnClickListener {
            showContentView()
            changeState(contentSelect)
        }
    }


    /**
     * 改变选中状态
     * @param select ObservableBoolean
     */
    private fun changeState(select: ObservableBoolean) {
        errorSelect.set(false)
        netWorkErrorSelect.set(false)
        emptySelect.set(false)
        loadingSelect.set(false)
        contentSelect.set(false)
        select.set(true)
    }

    override val layoutId: Int by lazy {
        R.layout.activity_page_state
    }

}