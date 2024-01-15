package io.ganguo.sample.viewmodel.service.image

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.databinding.WidgetRecyclerViewBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.core.viewmodel.common.widget.RecyclerVModel
import io.ganguo.log.core.Logger
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.entity.ImageEntity
import io.ganguo.utils.readString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

/**
 * <pre>
 *   @author : leo
 *   @time : 2020/10/13
 *   @desc : 图片加载框架页面
 * </pre>
 */
class ActivityImageLoaderSampleVModel(private val title: String) : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>(), CoroutineScope by MainScope() {
    private val imageList: MutableList<ImageEntity> by lazy {
        context.assets
                .open("jpgs.json")
                .readString()
                .let {
                    Logger.d("ActivityImageLoaderSampleVModel:${it}")
                    convertJson(it)
                }
    }

    private fun convertJson(data: String?): MutableList<ImageEntity> {
        data?.let {
            return Json.decodeFromString(ListSerializer(ImageEntity.serializer()), it).toMutableList()
        }
        return mutableListOf()
    }

    /**
     * 设置标题栏
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        HeaderTitleVModel
                .sampleTitleVModel(viewIF.activity, title)
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }


    /**
     * create RecyclerVModel
     * @return RecyclerVModel<*, *>
     */
    override fun createRecycleVModel(): RecyclerVModel<*, *> = let {
        RecyclerVModel.staggeredGridLayout<WidgetRecyclerViewBinding,
                ViewInterface<WidgetRecyclerViewBinding>>(
                context, 3, StaggeredGridLayoutManager.VERTICAL, 0
        )
    }

    /**
     * View初始化操作
     * @param view View
     */
    override fun onViewAttached(view: View) {
        addImageData()
    }

    /**
     * 添加图片数据
     */

    private fun addImageData() {
        val viewModels = imageList.map { ItemImageLoaderVModel(it.urls.small) }
        adapter.addAll(viewModels)
        toggleEmptyView()
    }
}
