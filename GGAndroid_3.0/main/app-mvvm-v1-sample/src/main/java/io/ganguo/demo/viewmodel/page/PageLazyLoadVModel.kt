package io.ganguo.demo.viewmodel.page

import io.ganguo.demo.viewmodel.item.ItemDemoVModel
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.utils.util.Randoms
import io.ganguo.utils.util.postDelayed
import io.ganguo.viewmodel.pack.base.viewmodel.BaseLazyHFSRecyclerVModel
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding
import io.ganguo.viewmodel.core.BaseViewModel

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/17
 *     desc   : 懒加载ViewModel
 * </pre>
 */
class PageLazyLoadVModel : BaseLazyHFSRecyclerVModel<ViewInterface<IncludeHfSwipeRecyclerBinding>>() {

    /**
     * 首次加载数据放该方法
     */
    override fun lazyLoadData() {
        context.postDelayed(1000) {
            addViewModel()
        }
    }


    /**
     * 添加ViewModel
     */
    private fun addViewModel() {
        for (i in 0..10) {
            adapter.add(newItemDemoVModel())
        }
        adapter.notifySetDataDiffChanged()
        toggleEmptyView()
    }

    /**
     * 生成 Item ViewModel
     *
     * @return
     */
    private fun newItemDemoVModel(): BaseViewModel<*> {
        val content = Randoms.getRandomCapitalLetters(12)
        return ItemDemoVModel<String>()
                .setBtnText("click")
                .setDataObj(content)
                .setContent(content)
                .setClickAction {
                    ToastHelper.showMessage("click")
                }
    }
}