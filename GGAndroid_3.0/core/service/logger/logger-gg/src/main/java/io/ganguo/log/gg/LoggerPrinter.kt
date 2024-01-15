package io.ganguo.log.gg

import android.util.Log
import io.ganguo.log.core.Printer
import java.util.*

/**
 * Created by hulkyao on 28/12/2016.
 */
open class LoggerPrinter : Printer {


    companion object {
        private const val DEFAULT_CURRENT_CLASS_POSITION = 2
        private const val DEFAULT_CURRENT_METHOD_POSITION = 2
        private const val DEFAULT_METHOD_COUNT = -1
        private const val DEFAULT_SHOW_EXTRA = false
        private const val DEFAULT_TAG = "default_tag"

        /**
         * logcat上面的区分标示
         */
        private const val THREAD_TITLE = "Thread : "
        private const val DIVIDER = "********************************************************************************"
    }

    /**
     * 本地的配置参数
     */
    private val showExtraLocal = ThreadLocal<Boolean?>()
    private val tagLocal = ThreadLocal<String?>()
    private val methodLocal = ThreadLocal<Int?>()

    /**
     * 构造器，初始化本地数据
     */
    init {
        showExtraLocal.set(DEFAULT_SHOW_EXTRA)
        tagLocal.set(DEFAULT_TAG)
        methodLocal.set(DEFAULT_METHOD_COUNT)
    }

    override fun tag(tag: String?): Printer {
        tagLocal.set(tag)
        return this
    }

    override fun extra(showExtra: Boolean, method: Int): Printer {
        showExtraLocal.set(showExtra)
        methodLocal.set(method)
        return this
    }

    override fun d(message: String?, vararg args: Any?) {
        val msg = String.format(Locale.getDefault(), message!!, *args)
        println(Log.DEBUG, msg, null)
    }

    override fun d(message: Any?) {
        println(Log.DEBUG, message, null)
    }

    override fun e(message: String?, vararg args: Any?) {
        val msg = String.format(Locale.getDefault(), message!!, *args)
        println(Log.ERROR, msg, null)
    }

    override fun e(throwable: Throwable?, message: String?, vararg args: Any?) {
        val msg = String.format(Locale.getDefault(), message!!, *args)
        println(Log.ERROR, msg, throwable)
    }

    override fun e(throwable: Throwable?, message: Any?) {
        println(Log.ERROR, message, throwable)
    }

    override fun e(message: Any?) {
        println(Log.ERROR, message, null)
    }

    override fun w(message: String?, vararg args: Any?) {
        val msg = String.format(Locale.getDefault(), message!!, *args)
        println(Log.WARN, msg, null)
    }

    override fun w(message: Any?) {
        println(Log.WARN, message, null)
    }

    override fun i(message: String?, vararg args: Any?) {
        val msg = String.format(Locale.getDefault(), message!!, *args)
        println(Log.INFO, msg, null)
    }

    override fun i(message: Any?) {
        println(Log.INFO, message, null)
    }

    override fun v(message: String?, vararg args: Any?) {
        val msg = String.format(Locale.getDefault(), message!!, *args)
        println(Log.VERBOSE, msg, null)
    }

    override fun v(message: Any?) {
        println(Log.VERBOSE, message, null)
    }

    /**
     * 按级别输出日志到Console中
     *
     * @param priority
     * @param msg
     * @param tr
     * @return
     */
    open fun println(priority: Int, msg: Any?, tr: Throwable?) {
        val thread = Thread.currentThread()
        val threadName = thread.name
        val elements = thread.stackTrace
        val globalShowExtra = GLogConfig.PRINT_STACK_INFO && priority >= GLogConfig.STACK_PRIORITY
        val showExtraMessage = localShowExtra || globalShowExtra
        val currentPosition = getCurrentClassPosition(elements)

        val currentMethodPosition = if (showExtraMessage) {
            DEFAULT_CURRENT_METHOD_POSITION
        } else {
            DEFAULT_CURRENT_METHOD_POSITION + 1
        }

        val methodStackOffset = currentPosition + currentMethodPosition
        val tagTitle: String = GLogConfig.TAG_PREFIX + "#"
        val globalTag = getSimpleClassName(elements[methodStackOffset].className) + "." + elements[methodStackOffset].methodName + "() "
        val localTag = localTag
        val tag = if (DEFAULT_TAG == localTag) {
            tagTitle + globalTag
        } else {
            tagTitle + localTag
        }
        if (showExtraMessage) {
            printDivider(tag, priority)
        }
        if (showExtraMessage) {
            printHeader(threadName, elements, methodStackOffset, tag, priority, tr)
        }
        printContent(tag, priority, msg, tr)
        if (showExtraMessage) {
            printDivider(tag, priority)
        }
    }

    private fun getCurrentClassPosition(elements: Array<StackTraceElement>): Int {
        for (temp in elements.indices) {
            val element = elements[temp]
            if (element.className == LoggerPrinter::class.java.name) {
                return temp
            }
        }
        return DEFAULT_CURRENT_CLASS_POSITION
    }

    /**
     * 输出扩展信息
     * @param threadName String
     * @param elements Array<StackTraceElement>
     * @param currentPosition Int
     * @param tag String
     * @param priority Int
     * @param tr Throwable?
     */
    private fun printHeader(threadName: String, elements: Array<StackTraceElement>, currentPosition: Int, tag: String, priority: Int, tr: Throwable?) {
        printContent(tag, priority, THREAD_TITLE + threadName, tr)
        var blank = ""
        val local = localMethod

        val method = if (local == -1) {
            GLogConfig.STACK_OFFSET
        } else {
            local
        }

        for (i in method - 1 downTo 0) {
            val element = elements[currentPosition + i]
            val builder = StringBuilder()
            builder.append(blank)
                    .append(getSimpleClassName(element.className))
                    .append(".")
                    .append(element.methodName)
                    .append(" ")
                    .append(" (")
                    .append(element.fileName)
                    .append(":")
                    .append(element.lineNumber)
                    .append(")")
            blank += "  "
            printContent(tag, priority, builder, tr)
        }
    }

    /**
     * 输出内容
     * @param tag String
     * @param priority Int
     * @param msg Any?
     * @param tr Throwable?
     */
    private fun printContent(tag: String, priority: Int, msg: Any?, tr: Throwable?) {
        when (priority) {
            Log.VERBOSE -> GLog.v(tag, msg, tr)
            Log.DEBUG -> GLog.d(tag, msg, tr)
            Log.INFO -> GLog.i(tag, msg, tr)
            Log.WARN -> GLog.w(tag, msg, tr)
            Log.ERROR -> GLog.e(tag, msg, tr)
        }
    }

    /**
     * 输出分界线
     * @param tag String
     * @param priority Int
     */
    private fun printDivider(tag: String, priority: Int) {
        printContent(tag, priority, DIVIDER, null)
    }

    /**
     * 获取类名
     * @param className String
     * @return String
     */
    private fun getSimpleClassName(className: String): String {
        val lastIndex = className.lastIndexOf(".")
        return className.substring(lastIndex + 1)
    }

    private val localTag: String
        get() {
            val tag = (if (tagLocal.get() == null) DEFAULT_TAG else tagLocal.get()!!)
            if (DEFAULT_TAG != tag) {
                tagLocal.set(DEFAULT_TAG)
                return tag
            }
            return DEFAULT_TAG
        }

    private val localMethod: Int
        get() {
            val method = (if (methodLocal.get() == null) DEFAULT_METHOD_COUNT else methodLocal.get()!!)
            if (method != DEFAULT_METHOD_COUNT) {
                methodLocal.set(DEFAULT_METHOD_COUNT)
                return method
            }
            return DEFAULT_METHOD_COUNT
        }

    private val localShowExtra: Boolean
        get() {
            val showExtra = (if (showExtraLocal.get() == null) false else showExtraLocal.get()!!)
            if (showExtra) {
                showExtraLocal.set(DEFAULT_SHOW_EXTRA)
                return true
            }
            return false
        }


}