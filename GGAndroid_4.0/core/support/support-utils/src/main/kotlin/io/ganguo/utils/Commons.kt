@file:JvmName("Commons")

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/18
 *     desc   : 常用工具函数
 * </pre>
 */
package io.ganguo.utils

import android.app.Activity

/**
 * 判断两个对象是否是，同一个类
 * @receiver Class<T>
 * @param clazz Class<T>
 * @return Boolean
 */
fun <T> Class<T>.isEquals(clazz: Class<out Activity>): Boolean {
    return javaClass == clazz
}