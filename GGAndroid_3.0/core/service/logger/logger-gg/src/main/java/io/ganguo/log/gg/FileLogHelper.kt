@file:JvmName("FileLogHelper")

/**
 * <pre>
 * author : leo
 * time   : 2018/12/27
 * desc   : FileLogHelper工具
 */
package io.ganguo.log.gg


import android.content.Context
import java.io.File
import java.io.IOException

/**
 * 创建一个Log File 文件
 * @receiver Context
 * @return File
 */
internal fun Context.createNewLogFile(): File {
    val logPath = getLogPath()
    for (i in 1..99) {
        val logFile = File(logPath, "log$i.txt")
        if (!logFile.exists()) {
            try {
                logFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return logFile
        } else {
            if (logFile.length() < GLogConfig.FILE_MAX_LENGTH) {
                return logFile
            }
        }
    }
    // delete all
    logPath.deleteFile()
    return File(logPath.toString() + "log1.txt")
}

/**
 * 删除目录中的所有文件
 */
private fun File?.deleteFile(): Boolean {
    // check
    if (this == null) {
        return false
    }
    if (!exists()) {
        return true
    }
    // delete all
    if (isDirectory) {
        listFiles().orEmpty().deleteFiles()
    }
    return delete()
}

/**
 * 删除目录中的所有文件
 */
private fun Array<out File>.deleteFiles() {
    for (child in this) {
        child.listFiles().orEmpty().deleteFiles()
    }
}

/**
 * 获取程序图片目录
 *
 * @return
 */
private fun Context.getLogPath(): File {
    return getStorageDirectory(GLogConfig.DATA_PATH + File.separator + GLogConfig.APP_LOG_PATH)
}

/**
 * 获取存储目录
 * # CacheDir ganguo
 *
 * @param dirName
 * @return
 */
private fun Context.getStorageDirectory(dirName: String): File {
    var file = File(this.cacheDir.toString() + File.separator + dirName)
    if (!file.exists()) {
        file.mkdirs()
    }
    return file
}
