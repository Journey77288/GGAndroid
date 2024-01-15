package io.ganguo.demo.viewmodel.page

import android.view.View
import io.ganguo.demo.viewmodel.item.ItemCityVModel
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.rx.RxActions
import io.ganguo.sticky_header.IStickyHeaderOnDrawListener
import io.ganguo.sticky_header.StickyHeaderDecoration
import io.ganguo.sticky_header.StickyHeaderHandler
import io.ganguo.viewmodel.pack.common.HFRecyclerViewModel
import io.ganguo.viewmodel.pack.common.RecyclerViewModel
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding
import io.ganguo.viewmodel.pack.RxVMLifecycle
import io.ganguo.viewmodel.databinding.IncludeRecyclerBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.functions.Functions
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

/**
 * <pre>
 * author : leo
 * time   : 2019/02/26
 * desc   : 城市列表
</pre> *
 */
class SideCityListVModel : HFRecyclerViewModel<ViewInterface<IncludeHfRecyclerBinding>>(), IStickyHeaderOnDrawListener {
    private val ctiy = arrayOf("安徽", "安化", "安阳", "鞍山", "安康", "安庆", "北京", "巴东县", "巴中", "白水县", "白云山", "B保定", "C长沙", "长安", "长白山", "长乐", "长寿区", "常德", "大安市", "大方县", "大关县", "大理市", "大连", "大田县", "大庆", "鄂州", "恩平市", "恩施市", "额敏县", "洱源县", "峨眉山市", "K高雄", "K开封", "K开化县", "K开江县", "K开鲁县", "K开阳县", "K开州区", "K凯里市", "N内江", "N内丘县", "N内乡县", "N那曲县", "乃东区", "南安市", "N南澳县", "W万安县", "万年县", "万全区", "万州区", "W旺苍县", "W望江县", "W威海", "威县", "W威信县", "乐清", "雅安", "烟台", "亚东县", "延安", "Y延边", "Y延长县", "Y延川县", "Y延寿县", "Y延吉市", "Z舟山", "Z周村", "Z周庄", "Z株洲", "Z珠海", "Z竹山县", "Z资兴县", "资阳", "Z子长县", "Z紫阳县")
    private var latterPositionMap = HashMap<String, Int>()

    override fun onViewAttached(view: View) {
        initCity()
    }


    override fun initRecyclerViewModel(): RecyclerViewModel<IncludeRecyclerBinding, ViewInterface<IncludeRecyclerBinding>> {
        var viewModel = super.initRecyclerViewModel()
        viewModel.itemDecoration(StickyHeaderDecoration(this))
        return viewModel
    }

    /**
     * 初始化城市列表数据
     */
    private fun initCity() {
        Observable
                .fromArray(*ctiy)
                .subscribeOn(Schedulers.io())
                .map { s -> ItemCityVModel(s) }
                .toList()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { itemCityVModels ->
                    adapter.addAll(itemCityVModels)
                    adapter.notifySetDataDiffChanged()
                    toggleEmptyView()
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--initCityData--"))
                .addTo(lifecycleComposite)
    }

    /**
     * 索引到列表具体的位置
     * @param selectedValue String?
     */
    fun onScrollListPosition(selectedValue: String?) {
        var position = latterPositionMap[selectedValue]
        if (position == null) {
            position = indexOfCity(selectedValue)
        }
        if (position != null) {
            recyclerView.layoutManager!!.scrollToPosition(position)
        }
    }


    /**
     * 定位城市列表位置
     * 索引到列表具体的位置
     * @param value String
     * @return Int
     */
    private fun indexOfCity(value: String?): Int? {
        val data: MutableList<Any> = adapter.data.toMutableList()
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
     * 索引字母绘制回调
     * @param position Int 索引位置
     * @param latter String 索引字母
     */
    override fun onDrawStickyHeader(position: Int, latter: String) {
        latterPositionMap[latter] = position
    }

    /**
     * 索引字母绘制结束
     * @param endPosition Int
     */
    override fun onDrawStickyHeaderEnd(endPosition: Int) {
    }

}
