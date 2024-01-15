package io.ganguo.app.environment

import android.content.Context
import java.io.File

/**
 * <pre>
 *     author : leo
 *     time   : 2020/06/06
 *     desc   : Common environment configuration
 * </pre>
 */
abstract class AppEnvDelegate {
    /**
     * 开发环境配置信息
     * 根据app/build.gradle生成
     * @see isDebug 是否是调试包
     * @see isProduction 是否是生产环境包
     * @see isAlpha 是否是开发阶段包
     * @see isBeta 是否是公测版本
     * @see isPreview 是否是预览版本
     * @see versionCode 版本号
     * @see versionName 版本名称
     * @see packageName 应用包名
     * @see baseUrl 服务器api链接
     * @see logLevel 日志级别
     * @see applicationId 应用Id，一般情况等于包名
     * @see flavor 包渠道
     * @see buildType 编译类型
     * @see environment 版本环境
     */
    abstract val isDebug: Boolean
    abstract val isAlpha: Boolean
    abstract val isPreview: Boolean
    abstract val isBeta: Boolean
    abstract val isProduction: Boolean
    abstract val versionCode: Int
    abstract val versionName: String
    abstract val packageName: String
    abstract val baseUrl: String
    abstract val logLevel: Int
    abstract val applicationId: String
    abstract val flavor: String
    abstract val buildType: String
    abstract val environment: String
    protected lateinit var context: Context

    /**
     * 初始化
     * @param context Context
     */
    fun initialize(context: Context) {
        this.context = context
        initialize()
    }


    /**
     * 初始化
     */
    protected abstract fun initialize()


    /**
     * 获取cache path
     * @return File?
     */
    abstract fun getCacheFile(): File?

    /**
     * 清空app缓存数据
     */
    abstract fun clearCache()


    /**
     * 获取缓存大小
     * @return String
     */
    abstract fun getCacheLongSize(): Long

    /**
     * 获取缓存大小
     * @return String
     */
    abstract fun getCacheSize(): String


}
