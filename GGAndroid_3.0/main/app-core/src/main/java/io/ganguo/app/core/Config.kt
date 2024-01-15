package io.ganguo.app.core

import io.ganguo.core.context.BaseApp
import io.ganguo.core.context.BaseApp.Companion.me
import io.ganguo.utils.util.Files
import java.io.File

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 程序配置文件（读取和写入）
 * </pre>
 */
object Config {
    /**
     * 数据目录
     */
    var DATA_PATH = "GanGuo"

    /**
     * 图片目录
     */
    var IMAGES_PATH = "images"

    /**
     * 图片缓存目录 ImageLoader
     */
    var IMAGE_CACHE_PATH = "imageCache"

    /**
     * 临时目录名称
     */
    const val APP_TEMP_PATH = "temp"

    /**
     * 日志文件目录名称
     */
    const val APP_LOG_PATH = "logs"

    /**
     * 获取app数据目录
     *
     * @return
     */
    @kotlin.jvm.JvmStatic
    val dataPath: File = Files.getStorageDirectory(me(), DATA_PATH)

    /**
     * 获取程序图片目录
     *
     * @return
     */
    @kotlin.jvm.JvmStatic
    val imagePath: File = Files.getStorageDirectory(me(), DATA_PATH + File.separator + IMAGES_PATH)

    /**
     * 获取程序图片缓存目录 不可见图片 ImageLoader
     *
     * @return
     */
    @kotlin.jvm.JvmStatic
    var imageCachePath: File = Files.getStorageDirectory(me(), DATA_PATH + File.separator + IMAGE_CACHE_PATH)

    /**
     * 获取程序图片目录
     *
     * @return
     */
    var logPath: File = Files.getStorageDirectory(me(), DATA_PATH + File.separator + APP_LOG_PATH)

    /**
     * 获取程序临时目录
     *
     * @return
     */
    val tempPath: File
        get() = Files.getStorageDirectory(me(), DATA_PATH + File.separator + APP_TEMP_PATH)

    /**
     * 获取目录的所有大小
     *
     * @return
     */
    @kotlin.jvm.JvmStatic
    val dataSize: Long
        get() = Files.getFolderSize(dataPath)

    /**
     * 清空所有app数据
     */
    fun clearData() {
        val cacheFile = me<BaseApp>().cacheDir
        if (cacheFile != null) {
            Files.deleteFile(cacheFile)
        }
        Files.deleteFile(dataPath)
    }
}