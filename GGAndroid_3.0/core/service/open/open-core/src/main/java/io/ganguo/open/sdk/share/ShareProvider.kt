package io.ganguo.open.sdk.share

import io.ganguo.factory.IProvider

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   :
 * </pre>
 * @property RESULT 返回值类型
 * @property ShareService 分享服务接口
 */
interface ShareProvider<T : ShareService<*, *>> : IProvider<T>