package io.ganguo.http.use.creator

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/02
 *     desc   : Api服务创建器
 * </pre>
 */
interface ApiServiceCreator {

    /**
     * 创建Api服务
     * @param clazz Class<S>
     */
    fun <S> createApiService(clazz: Class<S>): S
}