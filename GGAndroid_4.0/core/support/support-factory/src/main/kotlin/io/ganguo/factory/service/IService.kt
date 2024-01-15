package io.ganguo.factory.service

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 服务者 -- 接口
 * </pre>
 * @property isRelease 资源是否释放
 */
interface IService {
    var isRelease: Boolean

    /**
     * 资源释放操作
     */
    fun release() {
        if (isRelease) {
            return
        }
        isRelease = true
    }

}