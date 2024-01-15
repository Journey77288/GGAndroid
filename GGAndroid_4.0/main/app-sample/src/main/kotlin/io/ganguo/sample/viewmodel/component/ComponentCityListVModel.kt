package io.ganguo.sample.viewmodel.component

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.databinding.bindItemDecoration
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.rxjava.printThrowable
import io.ganguo.sticky_header.IStickyHeaderOnDrawListener
import io.ganguo.sticky_header.StickyHeaderDecoration
import io.ganguo.sticky_header.StickyHeaderHandler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.internal.functions.Functions
import io.reactivex.rxjava3.kotlin.addTo

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/20
 *     desc   : SideBar Sample - City List
 * </pre>
 * 
 * 使用前注意在AppContext中使用[io.ganguo.sample.context.initializer.WidgetApplicationInitializer]初始化拼音转换库
 */
class ComponentCityListVModel : HFRecyclerVModel<ViewInterface<FrameHeaderContentFooterBinding>>(), IStickyHeaderOnDrawListener {
    private var latterPositionMap = HashMap<String, Int>()
    private val cities = arrayOf("安徽", "安化", "安阳", "鞍山", "安康", "安庆", "北京", "巴东县", "巴中", "白水县", "白云山", "保定", "长沙", "长安", "长白山", "长乐", "长寿区", "常德", "大安市", "大方县", "大关县", "大理市", "大连", "大田县", "大庆", "鄂州", "恩平市", "恩施市", "额敏县", "洱源县", "峨眉山市", "开封", "开化县", "开江县", "开鲁县", "开阳县", "开州区", "凯里市", "内江", "内丘县", "内乡县", "那曲县", "乃东区", "南安市", "南澳县", "万安县", "万年县", "万全区", "万州区", "旺苍县", "望江县", "威海", "威县", "威信县", "乐清", "雅安", "烟台", "亚东县", "延安", "延边", "延长县", "延川县", "延寿县", "延吉市", "舟山", "周村", "周庄", "株洲", "珠海", "竹山县", "资兴县", "资阳", "子长县", "紫阳县")

    /**
     * 配置RecyclerView
     *
     * @param view
     */
    override fun initRecycleView(view: RecyclerView) {
        super.initRecycleView(view)
        view.bindItemDecoration(StickyHeaderDecoration(this))
    }

    override fun onViewAttached(view: View) {
        initCityData()
    }

    /**
     * 初始化城市数据
     */
    private fun initCityData() {
        Observable.fromArray(*cities)
                .map { ItemCityVModel(it) }
                .toList()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    adapter.addAll(it)
                    toggleEmptyView()
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--initCityData--"))
                .addTo(lifecycleComposite)
    }

    /**
     * 索引到指定位置
     *
     * @param selectedValue
     */
    fun scrollToPosition(selectedValue: String?) {
        var position = latterPositionMap[selectedValue]
        if (position == null) {
            position = indexOfCity(selectedValue)
        }
        if (position != null) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            layoutManager.scrollToPositionWithOffset(position, 0)
        }
    }

    /**
     * 定位城市列表位置
     * 索引到列表具体的位置
     * @param value
     * @return Int?
     */
    private fun indexOfCity(value: String?): Int? {
        val data: MutableList<Any> = adapter.currentList.toMutableList()
        return data.filterIsInstance<StickyHeaderHandler>()
                .filter {
                    value != null && value == it.stickyHeaderConfig.content
                }
                .map {
                    data.indexOf(it)
                }
                .firstOrNull()
    }

    /**
     * 开始绘制粘性头部回调
     *
     * @param position
     * @param letter
     */
    override fun onDrawStickyHeader(position: Int, letter: String) {
        latterPositionMap[letter] = position
    }

    /**
     * 绘制粘性头部结束回调
     *
     * @param endPosition
     */
    override fun onDrawStickyHeaderEnd(endPosition: Int) {

    }
}