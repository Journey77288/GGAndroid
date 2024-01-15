package io.ganguo.factory.method

import android.app.Activity
import io.ganguo.factory.IProvider
import io.ganguo.factory.service.IService

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : 工厂方法基类
 * </pre>
 * @property isRelease 是否已经释放过资源
 */
abstract class AbstractMethod<Service : IService, Provider : IProvider<Service>> {
    open var isRelease: Boolean = true


    /**
     *  to Service
     *
     * @param activity Activity引用
     */
    protected abstract fun asService(activity: Activity, provider: Provider): Service


    /**
     * 资源释放
     */
    open fun release() {
        if (isRelease) {
            return
        }
        isRelease = true
    }

    /**
     * 重置isRelease状态
     */
    open fun resetRelease() {
        isRelease = false
    }


}