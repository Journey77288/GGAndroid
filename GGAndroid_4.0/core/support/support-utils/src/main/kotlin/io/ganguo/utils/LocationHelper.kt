package io.ganguo.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.location.*
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import io.ganguo.appcompat.Toasts.Companion.show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/06/04
 *     desc   : 定位工具类
 * </pre>
 */
class LocationHelper private constructor() {
    private lateinit var context: Application
    private lateinit var geocoder: Geocoder

    /**
     * 初始化
     *
     * @param context Application
     */
    private fun initialize(context: Application) {
        this.context = context
        this.geocoder = Geocoder(context, Locale.US)
    }

    /**
     * 获取当前定位信息
     *
     * @param scope LifecycleCoroutineScope 协程生命周期作用域
     * @param callback Action1<Location?> 定位成功回调
     */
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(scope: LifecycleCoroutineScope, callback: Action1<Location?>) {
        val lastKnownLocation = getLastKnownLocation()
        lastKnownLocation?.let {
            scope.launch(Dispatchers.Main) {
                callback.invoke(lastKnownLocation)
            }
            return
        }
        scope.launch(Dispatchers.IO) {
            val locationManager = getLocationManager()
            val listener = getLocationListener(scope, callback)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                locationManager.getCurrentLocation(LocationManager.NETWORK_PROVIDER, null, context.mainExecutor) {
                    scope.launch(Dispatchers.Main) {
                        callback.invoke(it)
                    }
                }
            } else {
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, listener, Looper.getMainLooper())
            }
            //定位超时处理
            postDelayed(LOCATE_TIMEOUT_DURATION) {
                val lastKnowLocation = getLastKnownLocation()
                scope.launch(Dispatchers.Main) {
                    if (lastKnowLocation == null) {
                        callback.invoke(null)
                    } else {
                        callback.invoke(lastKnowLocation)
                    }
                }
                releaseListener(listener)
            }
        }
    }

    /**
     * 获取定位服务
     *
     * @return LocationManager
     */
    private fun getLocationManager(): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    /**
     * 获取最近已知定位信息
     */
    @SuppressLint("MissingPermission")
    fun getLastKnownLocation(): Location? {
        val locationManager = getLocationManager()
        locationManager.allProviders?: return null// 部分机型存在空的情况
        var result: Location? = null
        for (provider in locationManager.allProviders) {
            val location = locationManager.getLastKnownLocation(provider) ?: continue
            if (result == null || result.accuracy < location.accuracy) {
                result = location
            }
        }
        return result
    }

    /**
     * 获取定位监听回调
     *
     * @param scope LifecycleCoroutineScope 协程生命周期作用域
     * @param callback Action1<Location?> 定位回调
     * @return
     */
    private fun getLocationListener(scope: LifecycleCoroutineScope, callback: Action1<Location?>): LocationListener {
        return object : LocationListener {
            override fun onLocationChanged(location: Location) {
                scope.launch(Dispatchers.Main) {
                    callback.invoke(location)
                }
                releaseListener(this)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

            }

            override fun onProviderEnabled(provider: String) {

            }

            override fun onProviderDisabled(provider: String) {
                scope.launch(Dispatchers.Main) {
                    callback.invoke(null)
                }
            }
        }
    }

    /**
     * 开始定位
     *
     * @param scope LifecycleCoroutineScope 协程生命周期作用域
     * @param callback Action1<Address?> 定位回调
     */
    private fun startLocate(scope: LifecycleCoroutineScope, callback: Action1<Address?>) {
        getCurrentLocation(scope) {
            if (it == null) {
                show(R.string.str_address_location_error)
                callback.invoke(null)
                return@getCurrentLocation
            }
            getLocationInfo(scope, it, callback)
        }
    }

    /**
     * 获取地址信息
     *
     * @param scope LifecycleCoroutineScope
     * @param location Location
     * @param callback Action1<Address?>
     */
    private fun getLocationInfo(scope: LifecycleCoroutineScope, location: Location, callback: Action1<Address?>) {
        scope.launch(Dispatchers.IO) {// 异步获取地址信息，避免ANR
            try {
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (addresses.isNullOrEmpty()) {
                    Looper.prepare()
                    show(R.string.str_address_location_error)
                    callback.invoke(null)
                    Looper.loop()
                    return@launch
                }
                scope.launch(Dispatchers.Main) {
                    callback.invoke(addresses[0])
                }
            } catch (e: Exception) {
                Log.e("Tag", "getLocationInfo error: ${e.message}")
                Looper.prepare()
                show(R.string.str_address_location_error)
                callback.invoke(null)
                Looper.loop()
            }
        }
    }

    /**
     * 判断定位服务是否已经打开
     *
     * @return Boolean
     */
    fun isLocationServiceOpen(): Boolean = let {
        getLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    /**
     * 获取开启定位服务Intent
     *
     * @return Intent
     */
    fun getGPSSettingIntent(): Intent = let {
        val intent = Intent()
        intent.action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
        intent
    }

    /**
     * 释放监听
     * 避免内存泄漏
     */
    private fun releaseListener(listener: LocationListener) {
        val locationManager = getLocationManager()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locationManager.removeNmeaListener(OnNmeaMessageListener { message, timestamp -> })
        }
        locationManager.removeUpdates(listener)
    }

    /**
     * @property [LOCATE_TIMEOUT_DURATION] 定位超时时长
     */
    companion object {
        private const val LOCATE_TIMEOUT_DURATION = 10000L
        private lateinit var instance: LocationHelper

        /**
         * 初始化
         *
         * @param context Context
         */
        @JvmStatic
        fun init(context: Application) {
            check(!::instance.isInitialized) { "Already initialized" }
            instance = LocationHelper()
            instance.initialize(context)
        }

        /**
         * 检查初始化
         */
        fun checkInit() {
            if (::instance.isInitialized) {
                "You have to initialize LocationHelper.init(context) in Application first!!"
            }
        }

        /**
         * 获取单例
         */
        fun get(): LocationHelper = let {
            checkInit()
            instance
        }
    }
}