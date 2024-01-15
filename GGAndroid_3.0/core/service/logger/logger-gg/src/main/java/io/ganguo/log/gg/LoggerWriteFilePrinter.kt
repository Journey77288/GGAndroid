package io.ganguo.log.gg

import android.content.Context
import android.util.Log
import java.io.BufferedWriter
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*

/**
 * 文件日志，按级别保存到文件中
 * Created by Tony on 4/4/15.
 */
class LoggerWriteFilePrinter constructor(var context: Context) : LoggerPrinter() {
    private var formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    /**
     * 保存到文件中，可以按日志级别（priority）进行保存
     * 一般只保存ERROR级别，放到应用内部存储 CacheDir
     * 每个文件保存200K log1.txt、log2.txt
     *
     * @param priority
     * @param msg
     * @param tr
     * @return
     */
    override fun println(priority: Int, msg: Any?, tr: Throwable?) {
        super.println(priority, msg, tr)
        // 保存日志
        if (priority >= GLogConfig.FILE_PRIORITY) {
            writeLog(toLogMessage(priority, msg, tr))
        }
    }

    /**
     * 追加日志
     *
     * @param msg
     */
    private fun writeLog(msg: String) {
        val logFile = context.createNewLogFile()
        // 获取日志文件失败
        var writer: BufferedWriter? = null
        try {
            // 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
            writer = BufferedWriter(OutputStreamWriter(FileOutputStream(logFile, true)))
            writer.write(msg)
            writer.newLine()
            writer.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (writer != null) {
                try {
                    writer.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun toLogMessage(priority: Int, msg: Any?, tr: Throwable?): String {
        val sb = StringBuffer()
        sb.append(formatter.format(Date(System.currentTimeMillis())))
        sb.append(" ")
        when (priority) {
            Log.VERBOSE -> sb.append("V/")
            Log.DEBUG -> sb.append("D/")
            Log.INFO -> sb.append("I/")
            Log.WARN -> sb.append("W/")
            Log.ERROR -> sb.append("E/")
            else -> sb.append(priority)
        }
        sb.append(": ")
        sb.append(msg)
        if (tr != null) {
            sb.append("\r\n")
            sb.append(Log.getStackTraceString(tr))
        }
        return sb.toString()
    }
}