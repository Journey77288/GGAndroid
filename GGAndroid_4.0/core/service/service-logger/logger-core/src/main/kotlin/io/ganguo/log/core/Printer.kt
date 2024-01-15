package io.ganguo.log.core

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2020/05/20
 *     desc   :
 * </pre>
 */

interface Printer {

    /**
     * 设置 tag
     * @param tag String?
     * @return Printer
     */
    fun tag(tag: String?): Printer

    /**
     * 设置扩展信息
     * @param showExtra Boolean
     * @param method Int
     * @return Printer
     */
    fun extra(showExtra: Boolean, method: Int): Printer

    /**
     * 输出 debug log
     * @param message String?
     * @param args Array<out Any?>
     */
    fun d(message: String?, vararg args: Any?)

    /**
     * 输出 debug log
     * @param message Any?
     */
    fun d(message: Any?)

    /**
     * 输出 error log
     * @param message String?
     * @param args Array<out Any?>
     */
    fun e(message: String?, vararg args: Any?)

    /**
     * 输出 error log
     * @param throwable Throwable?
     * @param message String?
     * @param args Array<out Any?>
     */
    fun e(throwable: Throwable?, message: String?, vararg args: Any?)

    /**
     * 输出 error log
     * @param throwable Throwable?
     * @param message Any?
     */
    fun e(throwable: Throwable?, message: Any?)

    /**
     * 输出 error log
     * @param message Any?
     */
    fun e(message: Any?)

    /**
     * 输出 warn log
     * @param message String?
     * @param args Array<out Any?>
     */
    fun w(message: String?, vararg args: Any?)

    /**
     * 输出 warn log
     * @param message Any?
     */
    fun w(message: Any?)

    /**
     * 输出 info log
     * @param message String?
     * @param args Array<out Any?>
     */
    fun i(message: String?, vararg args: Any?)

    /**
     * 输出 info log
     * @param message Any?
     */
    fun i(message: Any?)

    /**
     * 输出 verbose log
     * @param message String?
     * @param args Array<out Any?>
     */
    fun v(message: String?, vararg args: Any?)

    /**
     * 输出 verbose log
     * @param msg Any?
     */
    fun v(message: Any?)
}