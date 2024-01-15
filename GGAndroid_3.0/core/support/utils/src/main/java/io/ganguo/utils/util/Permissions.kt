package io.ganguo.utils.util

import android.app.Activity
import com.afollestad.assent.Permission
import com.afollestad.assent.askForPermissions
import com.afollestad.assent.isAllGranted
import io.ganguo.utils.util.Permissions.*

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/20
 *     desc   : 权限请求工具类
 * </pre>
 */

/**
 * @property STORAGE 内存读写权限
 * @property CAMERA 相机访问权限
 * @property LOCATION 定位权限
 * @property CONTACTS 联系人读写权限
 * @property CALL_LOG 读写通话记录权限
 * @property CALL 拨打电话权限
 */
enum class Permissions(var value: MutableList<Permission>) {
    UNKNOWN(mutableListOf()),
    STORAGE(
            mutableListOf(
                    Permission.WRITE_EXTERNAL_STORAGE,
                    Permission.READ_EXTERNAL_STORAGE
            )
    ),
    CAMERA(
            mutableListOf(
                    Permission.WRITE_EXTERNAL_STORAGE,
                    Permission.READ_EXTERNAL_STORAGE,
                    Permission.RECORD_AUDIO,
                    Permission.CAMERA
            )
    ),
    LOCATION(
            mutableListOf(
                    Permission.ACCESS_FINE_LOCATION,
                    Permission.ACCESS_COARSE_LOCATION
            )
    ),
    CONTACTS(
            mutableListOf(
                    Permission.READ_CONTACTS,
                    Permission.WRITE_CONTACTS
            )
    ),
    CALL_LOG(
            mutableListOf(
                    Permission.READ_CALL_LOG,
                    Permission.WRITE_CALL_LOG
            )
    ),
    CALL(
            mutableListOf(
                    Permission.CALL_PHONE
            )
    );

    companion object {
        /**
         * 自定义Permissions (预留，暂时不对外提供)
         * @param permissions MutableList<Permission>
         * @return Permissions
         */
        @JvmStatic
        private fun parse(permissions: MutableList<Permission>): Permissions {
            return values().singleOrNull {
                it.value == permissions
            } ?: UNKNOWN
        }
    }
}


/**
 * 检测权限
 * @param permissions Permissions 权限类型
 * @return
 */
fun Activity.checkPermissions(permissions: Permissions): Boolean {
    return checkPermissions(permissions.value)
}

/**
 * 检测权限
 * @param permissions 权限集合
 * @return
 */
fun Activity.checkPermissions(permissions: List<Permission>): Boolean {
    if (isAllGranted(*permissions.toTypedArray())) {
        return true
    }
    return false
}


/**
 * 请求拨打电话权限
 * @param resultCallback Function1<Boolean, Unit> 权限申请结果回调，true->成功，false->失败。
 *                       如之前已经获得权限，则resultCallback则回调结果为true，调用该代码前。
 *                       无须再判断是否已经获得过权限。
 *
 */
fun Activity.requestCallPermissions(resultCallback: (Boolean) -> Unit) {
    requestPermissions(Permissions.CALL, resultCallback)
}


/**
 * 请求通话记录读写权限
 * @param resultCallback Function1<Boolean, Unit> 权限申请结果回调，true->成功，false->失败。
 *                       如之前已经获得权限，则resultCallback则回调结果为true，调用该代码前。
 *                       无须再判断是否已经获得过权限。
 *
 */
fun Activity.requestCallRecordsPermissions(
        resultCallback: (Boolean) -> Unit
) {
    requestPermissions(Permissions.CALL_LOG, resultCallback)
}


/**
 * 请求联系人读写权限
 * @param resultCallback Function1<Boolean, Unit> 权限申请结果回调，true->成功，false->失败。
 *                       如之前已经获得权限，则resultCallback则回调结果为true，调用该代码前。
 *                       无须再判断是否已经获得过权限。
 */
fun Activity.requestContactsPermissions(resultCallback: (Boolean) -> Unit) {
    requestPermissions(Permissions.CONTACTS, resultCallback)
}

/**
 * 请求定位权限
 * @param resultCallback Function1<Boolean, Unit> 权限申请结果回调，true->成功，false->失败。
 *                       如之前已经获得权限，则resultCallback则回调结果为true，调用该代码前。
 *                       无须再判断是否已经获得过权限。
 */
fun Activity.requestLocationPermissions(resultCallback: (Boolean) -> Unit) {
    requestPermissions(Permissions.LOCATION, resultCallback)
}


/**
 * 请求内存读写权限
 * @param resultCallback Function1<Boolean, Unit> 权限申请结果回调，true->成功，false->失败。
 *                       如之前已经获得权限，则resultCallback则回调结果为true，调用该代码前。
 *                       无须再判断是否已经获得过权限。
 */
fun Activity.requestStoragePermissions(resultCallback: (Boolean) -> Unit) {
    requestPermissions(Permissions.STORAGE, resultCallback)
}


/**
 * 请求相机访问权限
 * @param resultCallback Function1<Boolean, Unit> 权限申请结果回调，true->成功，false->失败。
 *                       如之前已经获得权限，则resultCallback则回调结果为true，调用该代码前。
 *                       无须再判断是否已经获得过权限。
 */
fun Activity.requestCameraPermissions(resultCallback: (Boolean) -> Unit) {
    requestPermissions(Permissions.CAMERA, resultCallback)
}


/**
 * 请求访问权限
 * @param permissions Permissions 权限枚举类型
 * @param resultCallback Function1<Boolean, Unit> 权限申请结果回调，true->成功，false->失败。
 *                       如之前已经获得权限，则resultCallback则回调结果为true，调用该代码前。
 *                       无须再判断是否已经获得过权限。
 */
private inline fun Activity.requestPermissions(
        permissions: Permissions,
        crossinline resultCallback: (Boolean) -> Unit
) {
    requestPermissions(permissions.value, resultCallback)
}

/**
 * 请求访问权限
 * @param permissions MutableList<Permission> 权限类型集合
 * @param resultCallback Function1<Boolean, Unit> 权限申请结果回调，true->成功，false->失败。
 *                       如之前已经获得权限，则resultCallback则回调结果为true，调用该代码前。
 *                       无须再判断是否已经获得过权限。
 */
inline fun Activity.requestPermissions(
        permissions: List<Permission>,
        crossinline resultCallback: (Boolean) -> Unit
) {
    if (checkPermissions(permissions)) {
        resultCallback.invoke(true)
        return
    }
    askForPermissions(*permissions.toTypedArray()) {
        var success = it.isAllGranted(it.permissions)
        resultCallback.invoke(success)
    }
}





