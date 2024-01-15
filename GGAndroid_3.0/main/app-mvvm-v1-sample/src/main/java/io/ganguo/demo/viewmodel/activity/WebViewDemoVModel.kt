package io.ganguo.demo.viewmodel.activity


import android.app.Activity
import android.content.Context
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintManager
import android.view.View

import com.tencent.smtt.sdk.WebView

import io.ganguo.demo.R
import io.ganguo.demo.databinding.ActivityWebViewDemoBinding
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.pack.common.HeaderItemViewModel
import io.ganguo.viewmodel.pack.common.HeaderViewModel
import io.ganguo.viewmodel.pack.common.MaterialLoadingWebVModel
import io.ganguo.viewmodel.pack.common.base.BaseWebViewModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * WebView - ViewModel  Demo
 * Created by leo on 2018/11/12.
 */
open class WebViewDemoVModel : BaseViewModel<ViewInterface<ActivityWebViewDemoBinding>>() {
    override val layoutId: Int by lazy {
        R.layout.activity_web_view_demo
    }
    private val url = "https://www.jianshu.com/"
    var urlWebViewModel: MaterialLoadingWebVModel = newWebViewModel()

    override fun onViewAttached(view: View) {
        initHeader()

        ViewModelHelper.bind(viewInterface.binding.includeWebview, this, urlWebViewModel)
    }

    private fun initHeader() {
        ViewModelHelper.bind(viewInterface.binding.includeHeader, this, newHeaderVModel())
    }


    /**
     * 生成 header ViewModel
     *
     * @return
     */
    private fun newHeaderVModel(): HeaderViewModel {
        return HeaderViewModel.Builder()
                .appendItemCenter(HeaderItemViewModel.TitleItemViewModel(javaClass.simpleName))
                .appendItemLeft(HeaderItemViewModel.BackItemViewModel(viewInterface.context as Activity)
                        .action { (viewInterface.context as Activity).onBackPressed() })
                .appendItemRight(HeaderItemViewModel()
                        .paddingRes(R.dimen.dp_18)
                        .fontSize(getDimensionPixelSize(R.dimen.font_18))
                        .action { newWebPrintJob(urlWebViewModel.webView!!) }
                        .text("打印"))
                .build()
    }

    /**
     * function：创建文件打印服务
     *
     * @param webView
     */
    private fun newWebPrintJob(webView: WebView) {
        // Get a PrintManager instance
        val printManager = viewInterface.context
                .getSystemService(Context.PRINT_SERVICE) as PrintManager
        // Get a print adapter instance
        val printAdapter = webView.createPrintDocumentAdapter("test") as PrintDocumentAdapter
        // Create a print job with name and adapter instance
        val jobName = getString(R.string.app_name) + " Document"
        printManager.print(jobName, printAdapter,
                PrintAttributes.Builder().build())
    }

    /**
     * WebViewModel
     *
     * @return
     */
    private fun newWebViewModel(): MaterialLoadingWebVModel {
        return MaterialLoadingWebVModel(BaseWebViewModel.WebContentType.URL, url)
                .setShowCenterLoading<MaterialLoadingWebVModel>(true)
                .setShowHorizontalBar(false)
    }

}
