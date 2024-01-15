package io.ganguo.factory


/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : 检查工具接口
 * </pre>
 */
interface ICheck {

    /**
     * 检测异常
     * @return
     */
    fun checkException(): Throwable?

    /**
     * 捕获异常
     */
    fun catchException()
}