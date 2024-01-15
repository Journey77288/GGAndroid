package io.ganguo.factory

import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/13
 *     desc   : WeakReference 创建接口
 * </pre>
 */
interface IWeakReference<T> {
    var weak: WeakReference<T>?
}