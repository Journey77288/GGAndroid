package io.ganguo.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.callback.ExplainReasonCallback
import com.permissionx.guolindev.callback.ForwardToSettingsCallback
import io.ganguo.PermissionResult
import io.ganguo.PermissionsGather

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/20
 *     desc   : Permission request tools，Only Boolean results are returned
 * </pre>
 */


/**
 * Checks for permissions
 * @param permissions PermissionGather permission Gather
 * @return
 */
fun Activity.checkPermissions(permissions: PermissionsGather): Boolean {
    return checkPermissions(permissions.value)
}

/**
 * Checks for permissions
 * @param permissions List<Permission> PermissionCollection
 * @return
 */
fun Activity.checkPermissions(permissions: List<String>): Boolean {
    var isAllGranted = true
    permissions.forEach {
        if (!PermissionX.isGranted(this, it)) {
            isAllGranted = false
        }
    }
    return isAllGranted
}

/**
 * Checks for media visual selected permissions
 * Adaptive for Android 14 media visual selected permission
 * @receiver Activity
 * @param permissions List<String>
 * @return Boolean
 */
fun Activity.checkMediaVisualSelectedEnable(permissions: PermissionsGather): Boolean {
    return checkMediaVisualSelectedEnable(permissions.value)
}

/**
 * Checks for media visual selected permissions
 * Adaptive for Android 14 media visual selected permission
 * @receiver Activity
 * @param permissions List<String>
 * @return Boolean
 */
private fun Activity.checkMediaVisualSelectedEnable(permissions: List<String>): Boolean {
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU || !permissions.contains(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)) {
        return true
    }
    val isMediaVisualSelectedGranted: Boolean
    permissions.first { it == Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED }
        .let {
            isMediaVisualSelectedGranted = PermissionX.isGranted(this, it)
        }
    return isMediaVisualSelectedGranted
}

/**
 * Request call access
 * @param resultCallback Function1<Boolean, Unit> Permission request callback，true->successful，false->failure
 */
fun Activity.requestCallPermissions(explainCallback: ExplainReasonCallback? = null,
                                    forwardSettingsCallback: ForwardToSettingsCallback ?= null,
                                    resultCallback: (PermissionResult) -> Unit) {
    requestPermissions(PermissionsGather.CALL, explainCallback, forwardSettingsCallback, resultCallback)
}


/**
 * Check whether call records permission granted
 * @receiver Activity
 * @return Boolean
 */
fun Activity.isCallRecordsPermissionGranted(): Boolean {
    return checkPermissions(PermissionsGather.CALL_LOG)
}


/**
 * Request access to the contact
 * @param resultCallback Function1<Boolean, Unit> Permission request callback，true->successful，false->failure
 */
fun Activity.requestCallRecordsPermissions(explainCallback: ExplainReasonCallback?,
                                           forwardSettingsCallback: ForwardToSettingsCallback ?= null,
                                           resultCallback: (PermissionResult) -> Unit) {
    requestPermissions(PermissionsGather.CALL_LOG, explainCallback, forwardSettingsCallback, resultCallback)
}


/**
 * Check whether contacts permission granted
 * @receiver Activity
 * @return Boolean
 */
fun Activity.isContactsPermissionGranted(): Boolean {
    return checkPermissions(PermissionsGather.CONTACTS)
}


/**
 * Request call access
 * @param resultCallback Function1<Boolean, Unit> Permission request callback，true->successful，false->failure
 */
fun Activity.requestContactsPermissions(explainCallback: ExplainReasonCallback? = null,
                                        forwardSettingsCallback: ForwardToSettingsCallback ?= null,
                                        resultCallback: (PermissionResult) -> Unit) {
    requestPermissions(PermissionsGather.CONTACTS, explainCallback, forwardSettingsCallback, resultCallback)
}


/**
 * Check whether location permission granted
 * @receiver Activity
 * @return Boolean
 */
fun Activity.isLocationPermissionGranted(): Boolean {
    return checkPermissions(PermissionsGather.LOCATION)
}


/**
 * Request location access
 * @param explainCallback
 * @param forwardSettingsCallback
 * @param resultCallback Function1<Boolean, Unit> Permission request callback，true->successful，false->failure
 */
fun Activity.requestLocationPermissions(explainCallback: ExplainReasonCallback? = null,
                                        forwardSettingsCallback: ForwardToSettingsCallback ?= null,
                                        resultCallback: (PermissionResult) -> Unit) {
    requestPermissions(PermissionsGather.LOCATION, explainCallback, forwardSettingsCallback, resultCallback)
}


/**
 * Check whether image media permission granted
 * @receiver Activity
 * @return Boolean
 */
fun Activity.isImageMediaPermissionGranted(): Boolean {
    return checkPermissions(PermissionsGather.STORAGE_IMAGE) || checkMediaVisualSelectedEnable(PermissionsGather.STORAGE_IMAGE)
}


/**
 * Request image media access
 * @param explainCallback ExplainReasonCallback?
 * @param forwardSettingsCallback ForwardToSettingsCallback?
 * @param resultCallback Function1<PermissionResult, Unit>
 */
fun Activity.requestImageMediaPermission(explainCallback: ExplainReasonCallback? = null,
                                         forwardSettingsCallback: ForwardToSettingsCallback ?= null,
                                         resultCallback: (PermissionResult) -> Unit) {
    requestPermissions(PermissionsGather.STORAGE_IMAGE, explainCallback, forwardSettingsCallback, resultCallback)
}


/**
 * Check whether video media permission granted
 * @receiver Activity
 * @return Boolean
 */
fun Activity.isVideoMediaPermissionGranted(): Boolean {
    return checkPermissions(PermissionsGather.STORAGE_VIDEO) || checkMediaVisualSelectedEnable(PermissionsGather.STORAGE_VIDEO)
}


/**
 * Request video media access
 * @param explainCallback ExplainReasonCallback?
 * @param forwardSettingsCallback ForwardToSettingsCallback?
 * @param resultCallback Function1<PermissionResult, Unit>
 */
fun Activity.requestVideoMediaPermission(explainCallback: ExplainReasonCallback? = null,
                                         forwardSettingsCallback: ForwardToSettingsCallback ?= null,
                                         resultCallback: (PermissionResult) -> Unit) {
    requestPermissions(PermissionsGather.STORAGE_VIDEO, explainCallback, forwardSettingsCallback, resultCallback)
}


/**
 * Check whether video record permission granted
 *
 * @receiver Activity
 * @return Boolean
 */
fun Activity.isVideoRecordPermissionGranted(): Boolean {
    return checkPermissions(PermissionsGather.VIDEO_RECORD)
}


/**
 * Request video record access
 *
 * @receiver Activity
 * @param explainCallback ExplainReasonCallback?
 * @param forwardSettingsCallback ForwardToSettingsCallback?
 * @param resultCallback Function1<PermissionResult, Unit>
 */
fun Activity.requestVideoRecordPermissions(explainCallback: ExplainReasonCallback? = null,
                                           forwardSettingsCallback: ForwardToSettingsCallback ?= null,
                                           resultCallback: (PermissionResult) -> Unit) {
    requestPermissions(PermissionsGather.VIDEO_RECORD, explainCallback, forwardSettingsCallback, resultCallback)
}


/**
 * Check whether audio media permission granted
 * @receiver Activity
 * @return Boolean
 */
fun Activity.isAudioMediaPermissionGranted(): Boolean {
    return checkPermissions(PermissionsGather.STORAGE_AUDIO)
}


/**
 * Request audio media access
 * @param explainCallback ExplainReasonCallback?
 * @param forwardSettingsCallback ForwardToSettingsCallback?
 * @param resultCallback Function1<PermissionResult, Unit>
 */
fun Activity.requestAudioMediaPermission(explainCallback: ExplainReasonCallback? = null,
                                         forwardSettingsCallback: ForwardToSettingsCallback ?= null,
                                         resultCallback: (PermissionResult) -> Unit) {
    requestPermissions(PermissionsGather.STORAGE_AUDIO, explainCallback, forwardSettingsCallback, resultCallback)
}


/**
 * Check whether storage permission granted
 * @receiver Activity
 * @return Boolean
 */
fun Activity.isStoragePermissionGranted(): Boolean {
    return checkPermissions(PermissionsGather.STORAGE) || checkMediaVisualSelectedEnable(PermissionsGather.STORAGE)
}


/**
 * Request memory access
 * @param resultCallback Function1<Boolean, Unit> Permission request callback，true->successful，false->failure
 */
fun Activity.requestStoragePermissions(explainCallback: ExplainReasonCallback? = null,
                                       forwardSettingsCallback: ForwardToSettingsCallback ?= null,
                                       resultCallback: (PermissionResult) -> Unit) {
    requestPermissions(PermissionsGather.STORAGE, explainCallback, forwardSettingsCallback, resultCallback)
}


/**
 * Check whether camera permission granted
 * @receiver Activity
 * @return Boolean
 */
fun Activity.isCameraPermissionGranted(): Boolean {
    return checkPermissions(PermissionsGather.CAMERA)
}


/**
 * Request camera access
 * @param resultCallback Function1<Boolean, Unit> Permission request callback，true->successful，false->failure
 */
fun Activity.requestCameraPermissions(explainCallback: ExplainReasonCallback? = null,
                                      forwardSettingsCallback: ForwardToSettingsCallback ?= null,
                                      resultCallback: (PermissionResult) -> Unit) {
    requestPermissions(PermissionsGather.CAMERA, explainCallback, forwardSettingsCallback, resultCallback)
}

/**
 * Request mobile phone permission
 * @param permissions PermissionsGather
 * @param resultCallback Function1<Boolean, Unit> Permission request callback，true->successful，false->failure
 */
private inline fun Activity.requestPermissions(
    permissions: PermissionsGather,
    explainCallback: ExplainReasonCallback? = null,
    forwardSettingsCallback: ForwardToSettingsCallback ?= null,
    crossinline resultCallback: (PermissionResult) -> Unit
) {
    requestPermissions(permissions.value, resultCallback, explainCallback, forwardSettingsCallback)
}

/**
 * Request mobile phone permission
 * @param permissions List<String> - Permissions that need to request
 * @param resultCallback Function1<Boolean, Unit> - Permission request callback，true->successful，false->failure
 * @param explainCallback Int - The callback of explaining why request permissions
 * @param forwardSettingsCallback ForwardToSettingsCallback - Callback for entering application settings
 *
 * If the permission has been obtained before, the callback result is true.
 * In general, this function can be called directly without having to judge whether there is a permission before calling.
 */
inline fun Activity.requestPermissions(
    permissions: List<String>,
    crossinline resultCallback: (PermissionResult) -> Unit,
    explainCallback: ExplainReasonCallback? = null,
    forwardSettingsCallback: ForwardToSettingsCallback? = null
) {
    if (checkPermissions(permissions)) {
        resultCallback.invoke(PermissionResult(true, permissions))
        return
    }
    PermissionX.init(this as FragmentActivity)
        .permissions(permissions)
        .onExplainRequestReason(explainCallback)
        .onForwardToSettings(forwardSettingsCallback)
        .request { allGranted, grantedList, deniedList ->
            var granted = allGranted
            // 适配Android 14 READ_MEDIA_VISUAL_USER_SELECTED权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                if (permissions.contains(Manifest.permission.READ_MEDIA_IMAGES) || permissions.contains(Manifest.permission.READ_MEDIA_VIDEO)) {
                    granted = grantedList.contains(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
                }
            }
            resultCallback.invoke(PermissionResult(granted, permissions))
        }
}

/**
 * Default Explain Callback
 *
 * @param context Context
 * @return ExplainReasonCallback
 */
inline fun getDefaultExplainCallback(context: Context): ExplainReasonCallback {
    return ExplainReasonCallback { scope, deniedList ->
        scope.showRequestReasonDialog(deniedList, context.getString(R.string.str_permission_request_hint), context.getString(R.string.str_permission_request_accept), context.getString(R.string.str_permission_request_reject))
    }
}
