package io.ganguo.log.gg

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

/**
 * Android日志扩展类
 * 1.控制输出级别
 * 2.控制日志输出长度
 * 3.打印JSON时漂亮输出
 *
 *
 * Created by Tony on 4/4/15.
 */
object GLog {
    private const val MAX_LENGTH = 3500

    fun v(tag: String, msg: Any?, tr: Throwable?): Int {
        if (GLogConfig.PRIORITY > Log.VERBOSE) {
            return 0
        }
        val content = toString(msg)
        handleContentToLog(Log.VERBOSE, tag, content, tr)
        return 0
    }

    fun d(tag: String, msg: Any?, tr: Throwable?): Int {
        if (GLogConfig.PRIORITY > Log.DEBUG) {
            return 0
        }
        val content = toString(msg)
        handleContentToLog(Log.DEBUG, tag, content, tr)
        return 0
    }

    fun i(tag: String, msg: Any?, tr: Throwable?): Int {
        if (GLogConfig.PRIORITY > Log.INFO) {
            return 0
        }
        val content = toString(msg)
        handleContentToLog(Log.INFO, tag, content, tr)
        return 0
    }

    fun w(tag: String, msg: Any?, tr: Throwable?): Int {
        if (GLogConfig.PRIORITY > Log.WARN) {
            return 0
        }
        val content = toString(msg)
        handleContentToLog(Log.WARN, tag, content, tr)
        return 0
    }

    fun e(tag: String, msg: Any?, tr: Throwable?): Int {
        if (GLogConfig.PRIORITY > Log.ERROR) {
            return 0
        }
        val content = toString(msg)
        handleContentToLog(Log.ERROR, tag, content, tr)
        return 0
    }

    private fun toString(msg: Any?): String {
        return if (msg == null || msg.toString().isNullOrEmpty()) {
            "Empty/Null log content"
        } else {
            toPrettyFormat(msg.toString())
        }
    }

    /**
     * 格式化 json
     * @param json String
     * @return String
     */
    private fun toPrettyFormat(json: String): String {
        var message: String
        message = try {
            if (json.startsWith("{\"") && json.endsWith("}")) {
                "JSONObject\r\n${JSONObject(json).toString(GLogConfig.JSON_INDENT)}"
            } else if (json.startsWith("[{\"") && json.endsWith("}]")) {
                "JSONObject\r\n${JSONArray(json).toString(GLogConfig.JSON_INDENT)}"
            } else {
                json
            }
        } catch (e: Exception) {
            json
        }
        if (GLogConfig.MAX_LENGTH > 0 && message.length > GLogConfig.MAX_LENGTH) {
            message = message.substring(0, GLogConfig.MAX_LENGTH)
        }
        return message
    }

    private fun handleContentToLog(priority: Int, tag: String, content: String, tr: Throwable?) {
        if (content.length > MAX_LENGTH) {
            val contentSplit = handleStringLength(content)
            for (split in contentSplit) {
                toLog(priority, tag, split, tr)
            }
        } else {
            toLog(priority, tag, content, tr)
        }
    }

    /**
     * 根据 MAX_LENGTH 拆分 String
     * @param content String
     * @return Array<String?>
     */
    private fun handleStringLength(content: String): Array<String?> {
        val offset = if (content.length % MAX_LENGTH > 0) 1 else 0
        val splitSize = content.length / MAX_LENGTH + offset
        val splits = arrayOfNulls<String>(splitSize)
        for (i in 0 until splitSize) {
            if (i == splitSize - 1) {
                splits[i] = content.substring(i * MAX_LENGTH, content.length)
            } else {
                splits[i] = content.substring(i * MAX_LENGTH, (i + 1) * MAX_LENGTH)
            }
        }
        return splits
    }

    /**
     * print log
     * @param priority Int
     * @param tag String
     * @param fromContent String?
     * @param tr Throwable?
     */
    private fun toLog(priority: Int, tag: String, fromContent: String?, tr: Throwable?) {
        var content = fromContent.orEmpty()
        when (priority) {
            Log.VERBOSE -> if (tr == null) {
                Log.v(tag, content)
            } else {
                Log.v(tag, content, tr)
            }
            Log.DEBUG -> if (tr == null) {
                Log.d(tag, content)
            } else {
                Log.d(tag, content, tr)
            }
            Log.INFO -> if (tr == null) {
                Log.i(tag, content)
            } else {
                Log.i(tag, content, tr)
            }
            Log.ERROR -> if (tr == null) {
                Log.e(tag, content)
            } else {
                Log.e(tag, content, tr)
            }
            Log.WARN -> if (tr == null) {
                Log.w(tag, content)
            } else {
                Log.w(tag, content, tr)
            }
        }
    }
}