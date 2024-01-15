package io.ganguo

import android.Manifest
import android.os.Build
import io.ganguo.PermissionsGather.CALL
import io.ganguo.PermissionsGather.CALL_LOG
import io.ganguo.PermissionsGather.CAMERA
import io.ganguo.PermissionsGather.CONTACTS
import io.ganguo.PermissionsGather.LOCATION
import io.ganguo.PermissionsGather.STORAGE
import io.ganguo.PermissionsGather.STORAGE_IMAGE
import io.ganguo.PermissionsGather.STORAGE_VIDEO
import java.security.Permission

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/25
 *     desc   : Generic permission type
 * </pre>
 */

/**
 * @property STORAGE_IMAGE 内存读写权限（仅图片）
 * @property STORAGE_VIDEO 内存读取权限（仅视频）
 * @property STORAGE 内存读写权限
 * @property CAMERA 相机访问权限
 * @property LOCATION 定位权限
 * @property CONTACTS 联系人读写权限
 * @property CALL_LOG 读写通话记录权限
 * @property CALL 拨打电话权限
 */
enum class PermissionsGather(var value: MutableList<String>) {
    UNKNOWN(mutableListOf()),
    STORAGE_IMAGE(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            mutableListOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableListOf(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            mutableListOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    ),
    STORAGE_VIDEO(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            mutableListOf(Manifest.permission.READ_MEDIA_VIDEO, Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableListOf(Manifest.permission.READ_MEDIA_VIDEO)
        } else {
            mutableListOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    ),
    STORAGE_AUDIO(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableListOf(Manifest.permission.READ_MEDIA_AUDIO)
        } else {
            mutableListOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    ),
    STORAGE(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            mutableListOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableListOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_VIDEO
            )
        } else {
            mutableListOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    ),
    CAMERA(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            mutableListOf(Manifest.permission.CAMERA)
        } else {
            mutableListOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        }
    ),
    VIDEO_RECORD(
        mutableListOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    ),
    LOCATION(
        mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    ),
    CONTACTS(
        mutableListOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
        )
    ),
    CALL_LOG(
        mutableListOf(
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG
        )
    ),
    CALL(
        mutableListOf(
            Manifest.permission.CALL_PHONE
        )
    );

    companion object {
        /**
         * 格式化 Permission
         * @param permissions MutableList<Permission>
         * @return Permissions
         */
        @JvmStatic
        private fun parse(permissions: MutableList<Permission>): PermissionsGather {
            return values().singleOrNull {
                it.value == permissions
            } ?: UNKNOWN
        }
    }
}
