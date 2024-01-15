package io.ganguo.app

import android.content.Context

/**
 * <pre>
 *     author : leo
 *     time   : 2020/06/06
 *     desc   : 通用环境变量配置接口
 * </pre>
 */
interface IAppEnv {
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
     * @see dataPath 文件保存路径
     * @see applicationId 应用Id，一般情况等于包名
     * @see flavor 包渠道
     * @see buildType 编译类型
     * @see environment 版本环境
     * @see packagingTime 打包时间
     */
    val isDebug: Boolean
    val isAlpha: Boolean
    val isPreview: Boolean
    val isBeta: Boolean
    val isProduction: Boolean
    val versionCode: Int
    val versionName: String
    val packageName: String
    val packagingTime: String
    val baseUrl: String
    val logLevel: Int
    val dataPath: String
    val applicationId: String
    val flavor: String
    val buildType: String
    val environment: String


    /**
     * 初始化
     * @param context Context
     */
    fun initialize(context: Context)
}