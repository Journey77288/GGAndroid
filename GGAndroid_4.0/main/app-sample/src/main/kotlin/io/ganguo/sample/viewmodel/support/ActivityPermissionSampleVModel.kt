package io.ganguo.sample.viewmodel.support

import android.view.View
import android.view.ViewGroup
import com.permissionx.guolindev.request.ForwardScope
import io.ganguo.PermissionResult
import io.ganguo.appcompat.Toasts
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.dialog.PermissionHintDialog
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.permission.*
import io.ganguo.sample.R
import io.ganguo.sample.viewmodel.ItemMenuVModel
import io.ganguo.utils.PermissionTimeHelper

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/08
 *     desc   : Permission Sample
 * </pre>
 */
class ActivityPermissionSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {
    private val storagePermissionDialog: PermissionHintDialog by lazy {
        PermissionHintDialog(
            context,
            getString(R.string.str_storage_permission_test_title),
            getString(R.string.str_storage_permission_test_hint)
        )
    }
    private val locationPermissionDialog: PermissionHintDialog by lazy {
        PermissionHintDialog(
            context,
            getString(R.string.str_location_permission_test_title),
            getString(R.string.str_location_permission_test_hint)
        )
    }
    private val cameraPermissionDialog: PermissionHintDialog by lazy {
        PermissionHintDialog(
            context,
            getString(R.string.str_camera_permission_test_title),
            getString(R.string.str_camera_permission_test_hint)
        )
    }

    /**
     * 设置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_permission_sample))
            .let {
                ViewModelHelper.bind(header.invoke(), this, it)
            }
    }

    override fun onViewAttached(view: View) {
        with(adapter) {
            addAll(
                listOf(
                    ItemMenuVModel.create(getString(R.string.str_request_storage_permission)) {
                        requestStoragePermission()
                    },
                    ItemMenuVModel.create(getString(R.string.str_request_location_permission)) {
                        requestLocationPermission()
                    },
                    ItemMenuVModel.create(getString(R.string.str_request_camera_permission)) {
                        requestCameraPermission()
                    }
                )
            )
        }
    }

    /**
     * 申请存储权限
     */
    private fun requestStoragePermission() {
        if (!viewIF.activity.isStoragePermissionGranted()) {
            if (PermissionTimeHelper.isEnableRequireStoragePermission()) {
                storagePermissionDialog.show()
                viewIF.activity.requestStoragePermissions(forwardSettingsCallback = { scope, deniedList ->
                    showForwardToSettingsDialog(scope, deniedList)
                }) {
                    storagePermissionDialog.dismiss()
                    if (!it.success) {
                        PermissionTimeHelper.updateStorageDeniedTimestamp()
                    }
                    onPermissionResult(it)
                }
            } else {
                Toasts.show(getString(R.string.str_storage_permission_test_denied))
            }
        }
    }

    /**
     * 申请位置信息权限
     */
    private fun requestLocationPermission() {
        if (!viewIF.activity.isLocationPermissionGranted()) {
            if (PermissionTimeHelper.isEnableRequireLocationPermission()) {
                locationPermissionDialog.show()
                viewIF.activity.requestLocationPermissions {
                    locationPermissionDialog.dismiss()
                    if (!it.success) {
                        PermissionTimeHelper.updateLocationDeniedTimestamp()
                    }
                    onPermissionResult(it)
                }
            } else {
                Toasts.show(getString(R.string.str_location_permission_test_denied))
            }
        }
    }

    /**
     * 申请相机权限
     */
    private fun requestCameraPermission() {
        if (!viewIF.activity.isCameraPermissionGranted()) {
            if (PermissionTimeHelper.isEnableRequireCameraPermission()) {
                cameraPermissionDialog.show()
                viewIF.activity.requestCameraPermissions(forwardSettingsCallback = { scope, deniedList ->
                    showForwardToSettingsDialog(scope, deniedList)
                }) {
                    cameraPermissionDialog.dismiss()
                    if (!it.success) {
                        PermissionTimeHelper.updateCameraDeniedTimestamp()
                    }
                    onPermissionResult(it)
                }
            } else {
                Toasts.show(getString(R.string.str_camera_permission_test_denied))
            }
        }
    }

    private fun showForwardToSettingsDialog(scope: ForwardScope, deniedList: List<String>) {
        scope.showForwardToSettingsDialog(deniedList, "Request Permission", "OK", "Cancel")
    }

    /**
     * 权限请求结果回调
     *
     * @param result 权限请求结果
     */
    private fun onPermissionResult(result: PermissionResult) {
        if (result.success) {
            Toasts.show(R.string.str_get_permission_success)
        } else {
            Toasts.show(R.string.str_get_permission_fail)
        }
    }
}