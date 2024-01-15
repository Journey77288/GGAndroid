package io.ganguo.http2.core.use.service

import io.ganguo.http.use.service.ApiServiceImpl

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/26
 *     desc   : 接口服务实现 companion object 基类
 * </pre>
 * @property [INSTANCE] 单例对象实例
 */
abstract class ApiServiceImplCompanion<S:Any, I : ApiServiceImpl<S>> {
    private val INSTANCE: I by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        createApiServiceImpl()
    }

    /**
     * 默认调用单例对象
     * @return I
     */
    fun get(): S {
        return INSTANCE.apiProxy!!
    }

    /**
     * 创建接口服务实现 实例对象
     * @return I
     */
    protected abstract fun createApiServiceImpl(): I
}