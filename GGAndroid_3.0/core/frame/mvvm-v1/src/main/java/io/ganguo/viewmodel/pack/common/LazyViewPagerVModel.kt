package io.ganguo.viewmodel.pack.common

import androidx.viewpager.widget.ViewPager
import io.ganguo.utils.helper.lazy.ILazyHandler
import io.ganguo.viewmodel.core.BaseViewModel

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/17
 *     desc   : 支持懒加载处理 ViewPager ViewModel
 * </pre>
 * @property defaultPosition 默认加载页
 */
class LazyViewPagerVModel(viewModels: List<BaseViewModel<*>>) : CommonViewPagerVModel(viewModels), ViewPager.OnPageChangeListener {
    var defaultPosition: Int = 0
    /**
     * 页面滑动状态回调
     * @param state Int
     */
    override fun onPageScrollStateChanged(state: Int) {
    }

    /**
     * 页面滑动中回调
     * @param position Int
     */
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    /**
     * 页面滑动结束回调
     * @param position Int
     */
    override fun onPageSelected(position: Int) {
        startLazyLoad(position)
    }

    /**
     * ViewPager init
     */
    override fun initViewPager() {
        super.initViewPager()
        viewPager.addOnPageChangeListener(this)
        startDefaultLazyLoad()
    }



    /**
     * 默认懒加载第一页
     */
    private fun startDefaultLazyLoad() {
        viewPager.post {
            setCurrentItem(defaultPosition)
            startLazyLoad(defaultPosition)
        }
    }


    /**
     * 开启position页懒加载数据
     * @param position
     */
    private fun startLazyLoad(position: Int) {
        var model: ILazyHandler? = getViewModel<Any>(position) as? ILazyHandler
                ?: return
        model?.lazyHelper?.lazyLoad()
    }
}