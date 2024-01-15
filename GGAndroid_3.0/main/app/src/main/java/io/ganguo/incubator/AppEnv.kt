package io.ganguo.incubator

import android.content.Context
import android.util.Log
import io.ganguo.app.IAppEnv
import io.ganguo.core.Config
import io.ganguo.log.core.Logger
import io.ganguo.log.gg.GLogConfig
import io.ganguo.utils.util.Networks
import io.ganguo.utils.util.Screens
import io.ganguo.utils.util.Systems

/**
 * <pre>
 * author : leo
 * time   : 2018/12/14
 * desc   : App 环境配置
 * </pre>
 */
object AppEnv : IAppEnv {
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
    override val isDebug: Boolean by lazy {
       BuildConfig.DEBUG
    }
    override val isAlpha: Boolean by lazy {
       BuildConfig.FLAVOR_PACKAGE_TYPE ==BuildConfig.ALPHA
    }
    override val isPreview: Boolean by lazy {
       BuildConfig.FLAVOR_PACKAGE_TYPE ==BuildConfig.PREVIEW
    }
    override val isBeta: Boolean by lazy {
       BuildConfig.FLAVOR_PACKAGE_TYPE ==BuildConfig.BETA
    }
    override val isProduction: Boolean by lazy {
       BuildConfig.FLAVOR_PACKAGE_TYPE ==BuildConfig.PRODUCTION
    }
    override val versionCode: Int by lazy {
       BuildConfig.VERSION_CODE
    }
    override val versionName: String by lazy {
       BuildConfig.VERSION_NAME
    }
    override val packageName: String by lazy {
       BuildConfig.APPLICATION_ID
    }
    override val packagingTime: String by lazy {
       BuildConfig.PACKAGING_TIME
    }
    override val baseUrl: String by lazy {
       BuildConfig.BASE_URL
    }
    override val logLevel: Int by lazy {
       BuildConfig.LOG_LEVEL
    }
    override val dataPath: String by lazy {
       BuildConfig.DATA_PATH
    }
    override val applicationId: String by lazy {
       BuildConfig.APPLICATION_ID
    }
    override val flavor: String by lazy {
       BuildConfig.FLAVOR
    }
    override val buildType: String by lazy {
       BuildConfig.BUILD_TYPE
    }
    override val environment: String by lazy {
       BuildConfig.ENVIRONMENT
    }


    /**
     * @see WECHAT_APP_ID 微信平台AppId
     * @see WECHAT_APP_SECRET 微信平台appSecret
     * @see QQ_APP_ID QQ平台AppId
     * @see SINA_APP_KEY 新浪微博平台appKey
     * @see SINA_REDIRECT_URL 新浪微博分享回调地址
     * @see SINA_SCOPE 新浪微博Scope
     * @see BUGLY_APPID bugly平台AppId
     */
    const val WECHAT_APP_ID: String =BuildConfig.WECHAT_APP_ID
    const val WECHAT_APP_SECRET: String =BuildConfig.WECHAT_APP_SECRET
    const val QQ_APP_ID: String =BuildConfig.QQ_APP_ID
    const val ALIPAY_APP_KEY: String =BuildConfig.ALIPAY_APP_KEY
    const val SINA_APP_KEY: String =BuildConfig.SINA_APP_KEY
    const val SINA_REDIRECT_URL: String =BuildConfig.SINA_REDIRECT_URL
    const val SINA_SCOPE: String =BuildConfig.SINA_SCOPE
    const val BUGLY_APPID: String =BuildConfig.BUGLY_APPID

    /**
     * init
     */
    override fun initialize(context: Context) {
        // App 数据保存目录
        Config.DATA_PATH = dataPath
        // 日志配置
        GLogConfig.PRIORITY = logLevel
        GLogConfig.FILE_PRIORITY = Log.ERROR

        // rider info
        Logger.i("****** APP_ENVIRONMENT ******")
        Logger.i(" APPLICATION_ID: $applicationId")
        Logger.i(" isDebug: $isDebug")
        Logger.i(" FLAVOR: $flavor")
        Logger.i(" BUILD_TYPE: $buildType")
        Logger.i(" VERSION_CODE: $versionCode")
        Logger.i(" VERSION_NAME: $versionName")
        Logger.i(" SCREEN_SIZE: " + Screens.getScreenWidth(context) + "x" + Screens.getScreenHeight(context))
        Logger.i(" DEVICE_PERFORMANCE: " + Systems.getPerformance(context))
        Logger.i(" BASE_URL: $baseUrl")
        Logger.i(" DATA_PATH: " + Config.DATA_PATH)
        Logger.i(" LOG_CONSOLE: " + GLogConfig.PRIORITY)
        Logger.i(" LOG_FILE: " + GLogConfig.FILE_PRIORITY)
        Logger.i(" IP_ADDRESS: " + Networks.getIpAddress(context).orEmpty())
        Logger.i("***************************")
    }

}
