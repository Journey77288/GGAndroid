package io.ganguo.permissionRx

import android.app.Activity
import com.permissionx.guolindev.callback.ExplainReasonCallback
import com.permissionx.guolindev.callback.ForwardToSettingsCallback
import io.ganguo.PermissionResult
import io.ganguo.PermissionsGather
import io.ganguo.PermissionsWrap
import io.ganguo.permission.requestPermissions
import io.ganguo.utils.postDelayed
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/19
 *   @desc   : Permission request tool is support RxJava
 * </pre>
 */


val wrap: PermissionsWrap = PermissionsWrap()

/**
 * Request call access
 */
fun Activity.requestCallPermissions(
    explainCallback: ExplainReasonCallback? = null,
    forwardSettingsCallback: ForwardToSettingsCallback? = null,
): Observable<PermissionResult> {
    return requestPermissions(PermissionsGather.CALL, explainCallback, forwardSettingsCallback)
}


/**
 * Request access to the contact
 */
fun Activity.requestCallRecordsPermissions(
    explainCallback: ExplainReasonCallback? = null,
    forwardSettingsCallback: ForwardToSettingsCallback? = null
): Observable<PermissionResult> {
    return requestPermissions(PermissionsGather.CALL_LOG, explainCallback, forwardSettingsCallback)
}


/**
 * Request call access
 */
fun Activity.requestContactsPermissions(
    explainCallback: ExplainReasonCallback? = null,
    forwardSettingsCallback: ForwardToSettingsCallback? = null
): Observable<PermissionResult> {
    return requestPermissions(PermissionsGather.CONTACTS, explainCallback, forwardSettingsCallback)
}

/**
 * Request location access
 */
fun Activity.requestLocationPermissions(
    explainCallback: ExplainReasonCallback? = null,
    forwardSettingsCallback: ForwardToSettingsCallback? = null
): Observable<PermissionResult> {
    return requestPermissions(PermissionsGather.LOCATION, explainCallback, forwardSettingsCallback)
}


/**
 * Request image media access
 * @param explainCallback ExplainReasonCallback?
 * @param forwardSettingsCallback ForwardToSettingsCallback?
 * @return Observable<PermissionResult>
 */
fun Activity.requestImageMediaPermission(
    explainCallback: ExplainReasonCallback? = null,
    forwardSettingsCallback: ForwardToSettingsCallback? = null
): Observable<PermissionResult> {
    return requestPermissions(PermissionsGather.STORAGE_IMAGE, explainCallback, forwardSettingsCallback)
}


/**
 * Request video media access
 * @param explainCallback ExplainReasonCallback?
 * @param forwardSettingsCallback ForwardToSettingsCallback?
 * @return Observable<PermissionResult>
 */
fun Activity.requestVideoMediaPermission(
    explainCallback: ExplainReasonCallback? = null,
    forwardSettingsCallback: ForwardToSettingsCallback? = null
): Observable<PermissionResult> {
    return requestPermissions(PermissionsGather.STORAGE_VIDEO, explainCallback, forwardSettingsCallback)
}


/**
 * Request audio media access
 * @receiver Activity
 * @param explainCallback ExplainReasonCallback?
 * @param forwardSettingsCallback ForwardToSettingsCallback?
 * @return Observable<PermissionResult>
 */
fun Activity.requestAudioMediaPermission(
    explainCallback: ExplainReasonCallback? = null,
    forwardSettingsCallback: ForwardToSettingsCallback? = null
): Observable<PermissionResult> {
    return requestPermissions(PermissionsGather.STORAGE_AUDIO, explainCallback, forwardSettingsCallback)
}


/**
 * Request memory access
 */
fun Activity.requestStoragePermissions(
    explainCallback: ExplainReasonCallback? = null,
    forwardSettingsCallback: ForwardToSettingsCallback? = null
): Observable<PermissionResult> {
    return requestPermissions(PermissionsGather.STORAGE, explainCallback, forwardSettingsCallback)
}


/**
 * Request camera access
 */
fun Activity.requestCameraPermissions(
    explainCallback: ExplainReasonCallback? = null,
    forwardSettingsCallback: ForwardToSettingsCallback? = null
): Observable<PermissionResult> {
    return requestPermissions(PermissionsGather.CAMERA, explainCallback, forwardSettingsCallback)
}


/**
 * Request mobile phone permission
 * @param permissions PermissionsGather
 */
private inline fun Activity.requestPermissions(
    permissions: PermissionsGather,
    explainCallback: ExplainReasonCallback? = null,
    forwardSettingsCallback: ForwardToSettingsCallback? = null
): Observable<PermissionResult> {
    return requestPermissions(permissions.value, explainCallback, forwardSettingsCallback)
}


/**
 * Request mobile phone permission
 *
 * @param permissions List<String> - Permissions that need to request
 * @param explainCallback Int - The callback of explaining why request permissions
 * @param forwardSettingsCallback ForwardToSettingsCallback - Callback for entering application settings
 *
 * If the permission has been obtained before, the callback result is true.
 * In general, this function can be called directly without having to judge whether there is a permission before calling.
 */
inline fun Activity.requestPermissions(permissions: List<String>,
                                       explainCallback: ExplainReasonCallback? = null,
                                       forwardSettingsCallback: ForwardToSettingsCallback ?= null): Observable<PermissionResult> {
    // 延时触发权限请求，避免rxPermissions为空的情况
    this.postDelayed(100) {
        requestPermissions(permissions, resultCallback = { result ->
            wrap.rxPermissions?.onNext(result)
            wrap.rxPermissions?.onComplete()
            wrap.rxPermissions = null
        }, explainCallback, forwardSettingsCallback)
    }
    wrap.rxPermissions = PublishSubject.create()
    return wrap.rxPermissions!!
}
