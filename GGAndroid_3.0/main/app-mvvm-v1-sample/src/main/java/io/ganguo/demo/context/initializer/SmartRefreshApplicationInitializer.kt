package io.ganguo.demo.context.initializer

import android.app.Application
import android.content.Context
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshInitializer
import io.ganguo.demo.R
import io.ganguo.core.context.initializer.ApplicationInitializer
import io.ganguo.core.context.initializer.ApplicationInitializerCreator
import io.ganguo.utils.helper.ResHelper
import io.ganguo.viewmodel.pack.common.loading.LoadingMaterialProgressVModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : SmartRefresh global Config
 * </pre>
 */
class SmartRefreshApplicationInitializer : ApplicationInitializer, DefaultRefreshInitializer {
    override fun initialize(application: Application) {
        SmartRefreshLayout.setDefaultRefreshInitializer(this)
    }


    override fun initialize(context: Context, layout: RefreshLayout) {
        layout.setPrimaryColors(ResHelper.getColor(R.color.colorPrimaryDark))
        //绑定Footer ViewModel
        val footerVModel = LoadingMaterialProgressVModel()
        ViewModelHelper.bind(ViewModelHelper.inflate(context, R.layout.include_material_progress_bar_load_more), footerVModel)
        layout.setFooterHeightPx(ResHelper.getDimensionPixelOffsets(R.dimen.dp_50))
        layout.setRefreshFooter(footerVModel)

        //绑定Header View
        val header = MaterialHeader(context)
        header.setColorSchemeColors(ResHelper.getColor(R.color.colorPrimaryDark))
        layout.setRefreshHeader(header)
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