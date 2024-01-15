package io.ganguo.factory

import android.content.Intent
import io.ganguo.factory.method.AbstractMethod
import io.ganguo.factory.result.IActivityResult
import io.ganguo.factory.result.IRxResultSend
import io.ganguo.factory.service.IService


/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 工厂模式 - 工具类
 * </pre>
 */
class GGFactory private constructor() {


    /**
     * 伴生对象
     * @property methodClassMap 方法总线
     * @property services [IService]数据集合
     */
    companion object : IFactory, IActivityResult {

        @JvmStatic
        override val methodClassMap: HashMap<Class<*>, AbstractMethod<*, *>> by lazy {
            HashMap<Class<*>, AbstractMethod<*, *>>()
        }

        @JvmStatic
        override val services: MutableList<IService> by lazy {
            mutableListOf<IService>()
        }


        /**
         * 注册工厂方法
         * @param method T
         * @return T
         */
        @Suppress("UNCHECKED_CAST")
        @JvmStatic
        override fun <T : AbstractMethod<*, *>> registerMethod(method: T): T {
            check(!methodClassMap.containsKey(method.javaClass)) { "duplicate register Method" }
            methodClassMap[method.javaClass] = method
            return method
        }

        /**
         * 获取已注册的工厂方法
         * @param c 类
         */
        @Suppress("UNCHECKED_CAST")
        @JvmStatic
        override fun <T : AbstractMethod<*, *>> getMethod(c: Class<*>): T {
            val method: Any = methodClassMap[c]
                    ?: throw IllegalArgumentException("Method " + c.name + "not register yet")
            return method as T
        }


        /**
         * 创建一个工厂服务对象
         * @param p
         * @return [IService]
         */
        @JvmStatic
        override fun <T : IService> newService(p: IProvider<T>): T {
            val service: T = p.newService()
            services.add(service)
            return service
        }

        /**
         * 删除工厂服务对象
         * @param p
         * @return [IService]
         */
        @JvmStatic
        override fun removeService(iService: IService?): Boolean {
            if (iService == null) {
                return false
            }
            if (methodClassMap.isNullOrEmpty()) {
                return false
            }
            if (!iService.isRelease) {
                iService.release()
            }
            if (services.contains(iService)) {
                services.remove(iService)
            }
            return true
        }

        /**
         * 清空工厂服务对象
         * @param p
         * @return [IService]
         */
        @JvmStatic
        override fun clearService() {
            if (services.isNullOrEmpty()) {
                return
            }
            for (service in services) {
                if (!service.isRelease) {
                    service.release()
                }
            }
            services.clear()
        }

        /**
         * 释放Method资源
         * @return [AbstractMethod]
         */
        override fun releaseMethod() {
            for (mutable in methodClassMap) {
                var method = mutable.value
                if (!method.isRelease) {
                    method.release()
                }
            }
        }

        /**
         * 注册ActivityResult回调
         * @param requestCode Int 请求code
         * @param resultCode Int 返回值
         * @param data Intent?
         */
        @JvmStatic
        override fun registerActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            for (method in methodClassMap) {
                var value = method.value
                if (value !is IActivityResult) {
                    continue
                }
                value.registerActivityResult(requestCode, resultCode, data)
            }
        }

        /**
         * get share ISendResult
         * @param c Class<*> 事件类型
         * @param Result Subject发射数据类型
         * @return IRxResultSend<Result>?
         */
        @Suppress("UNCHECKED_CAST")
        @JvmStatic
        fun <Result> asRxResultSend(c: Class<*>): IRxResultSend<Result>? {
            var method = getMethod<AbstractMethod<*, *>>(c)
            if (method is IRxResultSend<*>) {
                return method as IRxResultSend<Result>
            }
            return null
        }


    }
}