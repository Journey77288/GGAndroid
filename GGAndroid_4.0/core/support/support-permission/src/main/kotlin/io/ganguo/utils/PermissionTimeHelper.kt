package io.ganguo.utils

import io.ganguo.cache.sp.SPHelper

/**
 * <pre>
 *     author : lucas
 *     time   : 2023/04/13
 *     desc   : 权限申请时间管理 帮助类
 *     note   : 应用需要上架小米、vivo应用平台，隐私政策规定权限申请被拒绝后48小时内不能重复申请相同的权限，可以通过该帮助类来实现该
 * </pre>
 * @property DENIED_LIMIT_INTERVAL 权限申请被拒后不再申请相同权限的时间限制（48小时）
 * @property STORAGE_REQUIRE_DENIED_TIMESTAMP 存储权限最近一次申请被拒的时间戳
 * @property LOCATION_REQUIRE_DENIED_TIMESTAMP 定位权限最近一次申请被拒的时间戳
 * @property CAMERA_REQUIRE_DENIED_TIMESTAMP 相机权限最近一次申请被拒的时间戳
 * @property VIDEO_RECORD_REQUIRE_DENIED_TIMESTAMP 录像权限最近一次申请被拒的时间戳
 */
object PermissionTimeHelper {
    private const val DENIED_LIMIT_INTERVAL = 172800000L
    private const val STORAGE_REQUIRE_DENIED_TIMESTAMP = "storage_require_denied_timestamp"
    private const val LOCATION_REQUIRE_DENIED_TIMESTAMP = "location_require_denied_timestamp"
    private const val CAMERA_REQUIRE_DENIED_TIMESTAMP = "camera_require_denied_timestamp"
    private const val VIDEO_RECORD_REQUIRE_DENIED_TIMESTAMP = "video_record_require_denied_timestamp"

    /**
     * 判断是否可以请求存储权限
     *
     * @return Boolean
     */
    fun isEnableRequireStoragePermission(): Boolean {
        val lastTimestamp = SPHelper.getLong(STORAGE_REQUIRE_DENIED_TIMESTAMP, 0L)
        return System.currentTimeMillis() - lastTimestamp > DENIED_LIMIT_INTERVAL
    }

    /**
     * 更新最近一次存储权限被拒的时间戳
     */
    fun updateStorageDeniedTimestamp() {
        SPHelper.putLong(STORAGE_REQUIRE_DENIED_TIMESTAMP, System.currentTimeMillis())
    }

    /**
     * 判断是否可以请求定位权限
     *
     * @return Boolean
     */
    fun isEnableRequireLocationPermission(): Boolean {
        val lastTimestamp = SPHelper.getLong(LOCATION_REQUIRE_DENIED_TIMESTAMP, 0L)
        return System.currentTimeMillis() - lastTimestamp > DENIED_LIMIT_INTERVAL
    }

    /**
     * 更新最近一次定位权限请求被拒的时间戳
     */
    fun updateLocationDeniedTimestamp() {
        SPHelper.putLong(LOCATION_REQUIRE_DENIED_TIMESTAMP, System.currentTimeMillis())
    }

    /**
     * 判断是否可以请求摄像头权限
     *
     * @return Boolean
     */
    fun isEnableRequireCameraPermission(): Boolean {
        val lastTimestamp = SPHelper.getLong(CAMERA_REQUIRE_DENIED_TIMESTAMP, 0L)
        return System.currentTimeMillis() - lastTimestamp > DENIED_LIMIT_INTERVAL
    }

    /**
     * 更新最近一次摄像头权限请求被拒的时间戳
     */
    fun updateCameraDeniedTimestamp() {
        SPHelper.putLong(CAMERA_REQUIRE_DENIED_TIMESTAMP, System.currentTimeMillis())
    }

    /**
     * 判断是否可请求视频录像权限
     *
     * @return Boolean
     */
    fun isEnableRequireVideoRecordPermission(): Boolean {
        val lastTimestamp = SPHelper.getLong(VIDEO_RECORD_REQUIRE_DENIED_TIMESTAMP, 0L)
        return System.currentTimeMillis() - lastTimestamp > DENIED_LIMIT_INTERVAL
    }

    /**
     * 更新最近一次视频录制权限请求被拒的时间戳
     */
    fun updateVideoRecordDeniedTimestamp() {
        SPHelper.putLong(VIDEO_RECORD_REQUIRE_DENIED_TIMESTAMP, System.currentTimeMillis())
    }
}