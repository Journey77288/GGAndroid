package io.ganguo.incubator.viewmodel.general.activity

import android.os.Build
import android.view.View
import android.view.ViewGroup
import io.ganguo.app.core.lifecycle.ApkInfoPageObserver
import io.ganguo.incubator.AppEnv
import io.ganguo.incubator.R
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.utils.helper.ResHelper
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding
import io.ganguo.viewmodel.pack.base.viewmodel.BaseHFRecyclerVModel
import io.ganguo.viewmodel.pack.common.HeaderItemViewModel
import io.ganguo.viewmodel.pack.common.HeaderViewModel
import io.ganguo.viewmodel.pack.common.TextViewModel

/**
 * <pre>
 * author : leo
 * time   : 2018/9/15
 * desc   : Apk及手机相关参数展示 ViewModel
 * </pre>
 *
 */
class ApkInfoViewModel : BaseHFRecyclerVModel<ActivityInterface<IncludeHfRecyclerBinding>>() {

    init {
        addObserver()
    }

    override fun initHeader(container: ViewGroup) {
        super.initHeader(container)
        ViewModelHelper.bind(container, this, createHeaderVModel())
    }

    override fun onViewAttached(view: View) {
        addApkViewModel()
    }


    /**
     * 添加页面生命周期观察类
     */
    private fun addObserver() {
        lifecycle.addObserver(ApkInfoPageObserver(lifecycle) {
            //todo: refresh data
        })
    }

    /**
     * function：add apk info
     */
    private fun addApkViewModel() {
        adapter.add(createTextVModel(ResHelper.getString(R.string.str_app_package_info)))
        adapter.add(createTextVModel(ResHelper.getString(R.string.str_app_packaging_time, AppEnv.packagingTime)))
        adapter.add(createTextVModel(ResHelper.getString(R.string.str_app_package_name, AppEnv.packageName)))
        adapter.add(createTextVModel(ResHelper.getString(R.string.str_app_version_name, AppEnv.versionName)))
        adapter.add(createTextVModel(ResHelper.getString(R.string.str_app_version_code, AppEnv.versionCode)))
        adapter.add(createTextVModel(ResHelper.getString(R.string.str_app_packaging_environment, AppEnv.environment)))
        adapter.add(createTextVModel(ResHelper.getString(R.string.str_app_packaging_base_url, AppEnv.baseUrl)))
        adapter.add(createTextVModel(ResHelper.getString(R.string.str_app_version_sdk, Build.VERSION.SDK_INT)))
        adapter.add(createTextVModel(ResHelper.getString(R.string.str_app_sys_version, Build.VERSION.RELEASE)))
        adapter.add(createTextVModel(ResHelper.getString(R.string.str_app_device_model, Build.MODEL)))
        adapter.notifyDataSetChanged()
        toggleEmptyView()
    }


    /**
     * function：create header ViewModel
     *
     * @return
     */
    private fun createHeaderVModel(): BaseViewModel<*> {
        return HeaderViewModel.Builder()
                .appendItemLeft(HeaderItemViewModel.BackItemViewModel(viewInterface.activity)
                        .paddingRes(R.dimen.dp_16)
                        .drawableRes(android.R.drawable.ic_menu_close_clear_cancel))
                .appendItemCenter(HeaderItemViewModel.TitleItemViewModel(getString(R.string.str_app_packaging_apk_info)))
                .appendItemLeft(HeaderItemViewModel.BackItemViewModel(viewInterface.activity))
                .build()
    }


    /**
     * function: create TextViewModel
     *
     * @param text
     * @return
     */
    private fun createTextVModel(text: String): BaseViewModel<*> {
        return TextViewModel.Builder()
                .content(text)
                .textSizeRes(R.dimen.font_16)
                .paddingRightRes(R.dimen.dp_20)
                .paddingLeftRes(R.dimen.dp_20)
                .textColorRes(R.color.black)
                .width(ViewGroup.LayoutParams.MATCH_PARENT)
                .paddingBottomRes(R.dimen.dp_10)
                .paddingTopRes(R.dimen.dp_10)
                .build()
    }
}
