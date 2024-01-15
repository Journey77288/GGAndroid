package io.ganguo.core.viewmodel.common.widget

import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import io.ganguo.core.databinding.WidgetViewPagerBinding
import io.ganguo.lazy.ILazyHandler
import io.ganguo.mvvm.adapter.ViewModelAdapter
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.utils.postDelayed
import io.support.recyclerview.adapter.diffuitl.IDiffComparator

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/10/26
 *   @desc   : Lazy loading is supported ViewPager2VModel
 * </pre>
 *
 * use:
 *   @param PageViewModel must implement the [IDiffComparator] interface
 *   @param adapter In general, you can just use [ViewModelAdapter]
 *    var viewModel=ViewPager2LazyVModel(ViewModelAdapter(parent, ViewModelDiffHandler()))
 *
 *    viewModel.add(xxPageViewModel())
 *    viewModel.add(xxPageViewModel())
 *    or
 *    viewModel.addAll(mutableListOf(
 *                   xxPageHomeVModel(),
 *                   xxPageHomeVModel(),
 *                   xxPageHomeVModel(),
 *                   xxPageHomeVModel()))
 *
 *    viewModel.notifyDataSetChanged()
 *
 */
class ViewPager2LazyVModel<V : ViewInterface<WidgetViewPagerBinding>>(adapter: ViewModelAdapter) : ViewPager2VModel<V>(adapter) {

    /**
     * 默认调用懒加载
     */
    override fun setDefaultPage() {
        startDefaultLazyLoad()
    }

    /**
     * 页面滑动回调
     * @return BannerViewPager.OnPageChangeCallback
     */
    private fun getPageScrollCallback(): ViewPager2.OnPageChangeCallback = let {
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (adapter.currentList[position].isAttach()) {
                    startLazyLoad(position)
                }
            }
        }
    }


    /**
     * 默认懒加载第一页
     */
    private fun startDefaultLazyLoad() {
        viewPager.post {
            if (defaultPosition == 0) {// 第一页立即加载
                startLazyLoad(defaultPosition)
                addPageChangeCallback(getPageScrollCallback())
            } else {                   // 其他页面先跳转，在加载数据
                addPageChangeCallback(getPageScrollCallback())
                setCurrentItem(defaultPosition, false)
            }
        }
    }


    /**
     * 开启position页懒加载数据
     * @param position
     */
    fun startLazyLoad(position: Int) {
        if (position >= adapter.currentList.size) {
            return
        }
        var model: ILazyHandler? = adapter.currentList[position] as? ILazyHandler
                ?: return
        model?.lazyHelper?.lazyLoad()
    }


}
