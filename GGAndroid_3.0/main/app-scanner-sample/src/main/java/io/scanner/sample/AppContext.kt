package io.scanner.sample

import io.ganguo.image.core.ImageHelper
import io.ganguo.image.glide.engine.GlideImageEngine
import io.ganguo.core.context.BaseApp

/**
 * Created by leo on 2018/11/28.
 */
class AppContext : BaseApp() {


    override fun onCreate() {
        super.onCreate()
        initImageHelper()
    }
    /**
     * 初始化ImageHelper
     */
    private fun initImageHelper() {
        val builder = ImageHelper.Builder()
                .setEngine(GlideImageEngine())
        ImageHelper.init(builder)
    }
}
