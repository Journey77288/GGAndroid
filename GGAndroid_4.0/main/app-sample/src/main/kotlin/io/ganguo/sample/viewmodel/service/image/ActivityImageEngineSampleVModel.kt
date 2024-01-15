package io.ganguo.sample.viewmodel.service.image

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.component.PageLoadingVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.log.core.Logger
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.rxjava.printThrowable
import io.ganguo.rxresult.RxActivityResult
import io.ganguo.sample.bean.Keys
import io.ganguo.sample.context.AppContext
import io.ganguo.sample.view.service.image.ImageLoaderSampleActivity
import io.ganguo.sample.viewmodel.ItemMenuVModel
import io.image.ImageLoader
import io.image.coil.CoilImageEngine
import io.image.engine.ImageEngine
import io.image.glide.GlideImageEngine
import io.reactivex.rxjava3.internal.functions.Functions
import io.reactivex.rxjava3.kotlin.addTo

/**
 * <pre>
 *   @author : leo
 *   @time : 2020/10/14
 *   @desc : 图片加载引擎 - 配置demo
 * </pre>
 */
class ActivityImageEngineSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {

    init {
        addLoadingViewCreator(PageLoadingVModel(this))
    }

    /**
     * 设置标题栏
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        var headerVM = HeaderTitleVModel(false).apply {
            appendCenterItem(HeaderTitleVModel.textItemVModel("ImageEngine Sample"))
            appendLeftItem(HeaderTitleVModel.backImageViewModel(
                    this@ActivityImageEngineSampleVModel.viewIF.activity))
            backgroundColor(getColor(io.ganguo.resources.R.color.colorPrimary))
        }
        ViewModelHelper.bind(header.invoke(), this, headerVM)
    }


    override fun onViewAttached(view: View) {
        showLoadingView()
        with(adapter) {
            addAll(createGlideEngineVModel(), createCoilEngineVModel())
            toggleEmptyView()
        }
    }

    /**
     * 创建GlideImageEngine ViewModel
     * @return ViewModel<*>
     */
    private fun createGlideEngineVModel(): ViewModel<*> = ItemMenuVModel.create("Glide") {
        initImageEngine(GlideImageEngine(), "Glide Sample")
    }

    /**
     * 创建CoilImageEngine ViewModel
     * @return ViewModel<*>
     */
    private fun createCoilEngineVModel(): ViewModel<*> = ItemMenuVModel.create("Coil") {
        val engine = CoilImageEngine
                .Builder(AppContext.me())
                .supportGif(true)
                .supportSvg(true)
                .supportVideoFrame(true)
                .build()
        initImageEngine(engine, "Coil Sample")
    }


    /**
     * 初始化图片加载引擎
     * @param imageEngine ImageEngine
     */
    private fun initImageEngine(imageEngine: ImageEngine, engineName: String) {
        ImageLoader
                .Builder(imageEngine)
                .apply {
                    ImageLoader.init(this)
                }
        val intent = Intent(context, ImageLoaderSampleActivity::class.java)
        intent.putExtra(Keys.Intent.Common.DATA, engineName)
        RxActivityResult
                .startIntent(context as Activity, intent)
                .doOnNext {
                    Logger.d("RxActivityResult:${it}")
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--initImageEngine--"))
                .addTo(lifecycleComposite)
    }
}
