package io.ganguo.factory

import io.ganguo.factory.service.IService

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 供应者 - 接口
 * </pre>
 */
interface IProvider<T : IService> {


    /**
     * 创建一个IService服务
     * @return
     */
    fun newService(): T
}