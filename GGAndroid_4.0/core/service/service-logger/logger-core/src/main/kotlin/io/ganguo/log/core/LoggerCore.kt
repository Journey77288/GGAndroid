package io.ganguo.log.core

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/23
 *     desc   : Logger 打印配置
 * </pre>
 */
object LoggerCore {
    lateinit var printer: Printer

    /**
     *  LogConfig init
     * @param p Printer
     */
    fun initialize(p: Printer) {
        printer = p
    }
}