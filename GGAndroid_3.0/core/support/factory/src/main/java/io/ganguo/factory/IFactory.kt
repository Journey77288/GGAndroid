package io.ganguo.factory

import io.ganguo.factory.method.AbstractMethod
import io.ganguo.factory.service.IService

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 工厂方法工具类 - 接口
 * </pre>
 */
interface IFactory {
    val methodClassMap: HashMap<Class<*>, out AbstractMethod<*, *>>
    val services: MutableList<out IService>

    /**
     * 注册工厂方法
     * @param obj
     */
    fun <T : AbstractMethod<*, *>> registerMethod(method: T): T

    /**
     * 根据[Class]Key获取方法对象
     * @param obj
     * @return [T]
     */
    fun <T : AbstractMethod<*, *>> getMethod(c: Class<*>): T


    /**
     * 生成一个工厂服务对象
     * @param p
     * @return [IService]
     */
    fun <T : IService> newService(p: IProvider<T>): T

    /**
     * 删除方法总线中的服务对象
     * @param iService
     */
    fun removeService(iService: IService?): Boolean

    /**
     * 清空方法总线中的服务对象
     */
    fun clearService()


    /**
     * 释放Method相关资源
     */
    fun releaseMethod()

    /**
     * 释放Method及Service相关资源
     */
    fun release() {
        clearService()
        releaseMethod()
    }
}