package io.ganguo.utils

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/14
 *     desc   : 常用 Action
 * </pre>
 */

/**
 * 函数式编程，针对kotlin特性，使用匿名函数，代替之前接口实现
 */
typealias Action = () -> Unit
typealias Action1<T> = (T) -> Unit
typealias Action2<T1, T2> = (T1, T2) -> Unit
typealias Action3<T1, T2, T3> = (T1, T2, T3) -> Unit
typealias Action4<T1, T2, T3, T4> = (T1, T2, T3, T4) -> Unit
