package io.ganguo.sample.context.initializer

import android.app.Application
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import io.ganguo.app.context.initializer.ApplicationInitializer
import io.ganguo.app.context.initializer.ApplicationInitializerCreator
import io.ganguo.resources.R
import io.ganguo.core.viewmodel.common.component.LoadMoreLoadingVModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.resources.ResourcesHelper

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : SmartRefreshLayout global Config
 * </pre>
 */
class SmartRefreshApplicationInitializer : ApplicationInitializer {

    /**
     * Initialize in Application
     * @param application Application
     */
    override fun initialize(application: Application) {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            val header = MaterialHeader(context)
            header.setColorSchemeColors(ResourcesHelper.getColor(R.color.colorPrimaryDark))
            header
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setPrimaryColors(ResourcesHelper.getColor(R.color.colorPrimaryDark))
            layout.setFooterHeightPx(ResourcesHelper.getDimensionPixelOffset(R.dimen.dp_50))
            val footer = LoadMoreLoadingVModel()
            ViewModelHelper.bind(context, footer)
            footer
        }
    }

    companion object : ApplicationInitializerCreator<SmartRefreshApplicationInitializer> {

        /**
         * create ApplicationInitializerCreator
         * @param parameter Carry configuration parameters
         * @return  Pair<String, T>
         */
        override fun createInitializer(parameter: Map<String, Any>): SmartRefreshApplicationInitializer {
            return SmartRefreshApplicationInitializer()
        }

    }

}
