package io.ganguo.core

import io.ganguo.app.environment.AppEnvDelegate
import io.ganguo.utils.deleteFile
import io.ganguo.utils.folderSize
import io.ganguo.utils.formatFileSize
import java.io.File

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 程序环境配置文件（读取和写入）
 * </pre>
 */
abstract class AppCoreEnvironment : AppEnvDelegate() {
    /**
     * data cache file
     * @return File?
     */
    override fun getCacheFile(): File? {
        return context.cacheDir
    }

    /**
     * clear
     */
    override fun clearCache() {
        getCacheFile()?.deleteFile()
    }


    /**
     * cache size
     * @return String
     */
    override fun getCacheLongSize(): Long {
        return getCacheFile()?.folderSize()?: 0
    }

    /**
     * 获取缓存大小
     * @return String
     */
    override fun getCacheSize(): String {
        return getCacheLongSize().formatFileSize().orEmpty()
    }
}
