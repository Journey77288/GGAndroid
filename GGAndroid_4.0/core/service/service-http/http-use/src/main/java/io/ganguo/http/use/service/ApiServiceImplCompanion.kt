package io.ganguo.http.use.service

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/26
 *     desc   : 接口服务实现 companion object 基类
 * </pre>
 * @property [SINGLETON_INSTANCE] 单例对象实例
 */
abstract class ApiServiceImplCompanion<S:Any, I : ApiServiceImpl<S>> {
    private val SINGLETON_INSTANCE: I by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        createApiServiceImpl()
    }

    /**
     * 默认调用单例对象
     * @return I
     */
    fun get(): S {
        return SINGLETON_INSTANCE.apiProxy!!
    }

    /**
     * 创建接口服务实现 实例对象
     * @return I
     */
    protected abstract fun createApiServiceImpl(): I
}