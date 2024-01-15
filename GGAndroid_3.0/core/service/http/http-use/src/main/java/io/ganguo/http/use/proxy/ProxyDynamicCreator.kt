package io.ganguo.http2.core.use.proxy

import io.ganguo.http.use.proxy.IProxyCreator
import java.lang.IllegalArgumentException
import java.lang.reflect.InvocationHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/24
 *     desc   : 动态代理创建工具类
 *          注: 此处用到的设计模式为'动态代理设计模式'
 *          优点: 1、良好的扩展性。修改被代理角色并不影响调用者使用代理，对于调用者，被代理角色是透明的。
 *               2、隔离，降低耦合度。
 *          缺点: 增加了代理类，实现需要经过代理，因此请求速度会变慢。 (大部分场景可以忽略不计)
 * </pre>
 */
@Suppress("UNCHECKED_CAST")
open class ProxyDynamicCreator<T : Any>(override var target: T) : IProxyCreator<T>, InvocationHandler {

    /**
     * 对外提供创建代理服务对象函数
     * @return T
     */
    override fun createProxy(): T {
        val clazz = target.javaClass
        return Proxy.newProxyInstance(clazz.classLoader, clazz.interfaces, this) as T
    }


    /**
     * Proxy 创建代理具体实现
     * @param proxy Any
     * @param method Method
     * @param args Array<out Any>
     * @return Any
     */
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        var result: Any? = null
        try {
            result = method?.invoke(target, *(args ?: emptyArray()))
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return result
    }

}