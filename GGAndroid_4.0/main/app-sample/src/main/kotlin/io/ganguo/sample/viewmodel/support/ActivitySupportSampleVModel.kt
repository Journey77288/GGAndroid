package io.ganguo.sample.viewmodel.support

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.ganguo.appcompat.Toasts
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.databinding.WidgetRecyclerViewBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.core.viewmodel.common.widget.RecyclerVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.rxjava.printThrowable
import io.ganguo.rxresult.RxActivityResult
import io.ganguo.sample.R
import io.ganguo.sample.bean.Keys
import io.ganguo.sample.view.service.LocaleSampleActivity
import io.ganguo.sample.view.support.*
import io.ganguo.sample.view.support.cache.CacheSampleActivity
import io.ganguo.sample.view.support.download.DownloadSampleActivity
import io.ganguo.sample.view.support.rxbus.RxBusObserverSampleActivity
import io.ganguo.sample.view.support.skeleton.SkeletonSampleActivity
import io.ganguo.sample.viewmodel.ItemMenuVModel
import io.reactivex.rxjava3.internal.functions.Functions
import io.reactivex.rxjava3.kotlin.addTo

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/08
 *     desc   : Support Sample
 * </pre>
 */
class ActivitySupportSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {

    /**
     * 设置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_support_sample))
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    /**
     * create RecyclerVModel and bind to ViewBinding
     * @return RecyclerVModel<*, *>
     */
    override fun createRecycleVModel(): RecyclerVModel<*, *> = let {
        RecyclerVModel
                .gridLayout<WidgetRecyclerViewBinding, ViewInterface<WidgetRecyclerViewBinding>>(context, 3, LinearLayoutManager.VERTICAL, 0)
    }

    override fun onViewAttached(view: View) {
        adapter.addAll(
            listOf(
                ItemMenuVModel.create(getString(R.string.str_scheme_sample)) {
                    SchemeSampleActivity.start(context)
                },
                ItemMenuVModel.create(getString(R.string.str_cache_sample)) {
                    CacheSampleActivity.start(context)
                },
                ItemMenuVModel.create(getString(R.string.str_skeleton_sample)) {
                    SkeletonSampleActivity.start(context)
                },
                ItemMenuVModel.create(getString(R.string.str_permission_sample)) {
                    PermissionSampleActivity.start(context)
                },
                ItemMenuVModel.create(getString(R.string.str_rx_activity_result_sample)) {
                    toRxActivitySample()
                },
                ItemMenuVModel.create(getString(R.string.str_rx_bus_sample)) {
                    RxBusObserverSampleActivity.start(context)
                },
                ItemMenuVModel.create(getString(R.string.str_version_update_sample)) {
                    VersionUpdateSampleActivity.start(context)
                },
                ItemMenuVModel.create(getString(R.string.str_location_sample)) {
                    LocationSampleActivity.start(context)
                },
                ItemMenuVModel.create(getString(R.string.str_download_sample)) {
                    DownloadSampleActivity.start(context)
                },
                ItemMenuVModel.create(getString(R.string.str_language_sample)) {
                    LocaleSampleActivity.start(context)
                }
            )
        )
    }

    /**
     * enter RxActivitySampleActivity
     */
    private fun toRxActivitySample() {
        RxActivityResult.startIntent(viewIF.activity, RxActivityResultSampleActivity.intentFor(context))
                .filter { it.resultCode == Activity.RESULT_OK }
                .doOnNext {
                    val data = it.data?.getStringExtra(Keys.Intent.Common.DATA).orEmpty()
                    Toasts.show(data)
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--toRxActivitySample--"))
                .addTo(lifecycleComposite)
    }
}