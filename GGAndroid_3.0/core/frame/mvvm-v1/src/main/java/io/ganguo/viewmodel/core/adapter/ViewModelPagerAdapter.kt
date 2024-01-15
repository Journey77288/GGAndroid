package io.ganguo.viewmodel.core.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import java.util.*


/**
 * <pre>
 *    @author : leo
 *    time   : 2020/05/23
 *    desc   : Adapter DiffUtil Comparator
 * </pre>
 * @property pageViewModels MutableList<BaseViewModel<*>>
 * @property viewModelParent BaseViewModel<*>
 */
class ViewModelPagerAdapter : PagerAdapter {
    var pageViewModels: MutableList<BaseViewModel<*>> = ArrayList()
    private var viewModelParent: BaseViewModel<*>

    constructor(viewModelParent: BaseViewModel<*>) {
        this.viewModelParent = viewModelParent
    }

    constructor(parent: BaseViewModel<*>, items: MutableList<BaseViewModel<*>>) {
        pageViewModels.addAll(items)
        viewModelParent = parent
    }

    /**
     * get page size
     * @return Int
     */
    override fun getCount(): Int {
        return pageViewModels.size
    }

    /**
     * equals item
     * @param view View
     * @param item Any
     * @return Boolean
     */
    override fun isViewFromObject(view: View, item: Any): Boolean {
        return view === item
    }

    /**
     * instantiate Item and binding data
     * @param container ViewGroup
     * @param position Int
     * @return Any
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val viewModel = pageViewModels[position]
        ViewModelHelper.bind(container, viewModelParent, viewModel)
        return viewModel.rootView
    }

    /**
     * Item destroy
     * @param container ViewGroup
     * @param position Int
     * @param item Any
     */
    override fun destroyItem(container: ViewGroup, position: Int, item: Any) {
        container.removeView(item as View)
        pageViewModels[position].onDestroy()
    }


    /**
     * Set up a new data source
     * @param pageViewModels List<BaseViewModel<*>>
     * @return ViewModelPagerAdapter
     */
    fun setPageViewModels(pageViewModels: List<BaseViewModel<*>>): ViewModelPagerAdapter {
        this.pageViewModels.clear()
        this.pageViewModels.addAll(pageViewModels)
        return this
    }

    /**
     * add ViewModel
     * @param pageVModel BaseViewModel<ViewInterface<*>>
     */
    fun addViewModel(pageVModel: BaseViewModel<ViewInterface<*>>) {
        pageViewModels.add(pageVModel)
    }

    /**
     * get Page ViewModel
     * @param position Int
     * @return BaseViewModel<*>
     */
    fun getPageViewModel(position: Int): BaseViewModel<*> {
        return pageViewModels[position]
    }
}