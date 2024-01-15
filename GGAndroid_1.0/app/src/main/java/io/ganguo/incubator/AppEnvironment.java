package io.ganguo.incubator;

import android.util.Log;

import io.ganguo.library.Config;
import io.ganguo.library.util.log.FileLogger;
import io.ganguo.library.util.log.GLog;
import io.ganguo.library.util.log.LogConfig;

/**
 * App 环境配置
 * <p/>
 * Created by Tony on 3/4/15.
 */
public class AppEnvironment {

    /**
     * 开发环境配置信息
     * 根据app/build.gradle生成
     */
    public final static boolean DEV_MODE = BuildConfig.DEV_MODE;//是否处于测试环境
    public final static String BASE_URL = BuildConfig.BASE_URL;//server url
    public final static String MARKET_CHANNEL = BuildConfig.FLAVOR;//市场渠道

    /**
     * init
     */
    static {
        // App 数据保存目录
        Config.DATA_PATH = BuildConfig.DATA_PATH;
        // 日志配置
        LogConfig.LOGGER_CLASS = FileLogger.class;
        LogConfig.PRIORITY = DEV_MODE ? Log.VERBOSE : Log.INFO;
        LogConfig.FILE_PRIORITY = Log.ERROR;

        GLog.i("AppInfo", "****** AppEnvironment ******");
        GLog.i("AppInfo", " DEV_MODE: " + DEV_MODE);
        GLog.i("AppInfo", " APPLICATION_ID: " + BuildConfig.APPLICATION_ID);
        GLog.i("AppInfo", " FLAVOR: " + BuildConfig.FLAVOR);
        GLog.i("AppInfo", " VERSION_CODE: " + BuildConfig.VERSION_CODE);
        GLog.i("AppInfo", " VERSION_NAME: " + BuildConfig.VERSION_NAME);
        GLog.i("AppInfo", " BASE_URL: " + BASE_URL);
        GLog.i("AppInfo", " DATA_PATH: " + Config.DATA_PATH);
        GLog.i("AppInfo", " LOG_CONSOLE: " + LogConfig.PRIORITY);
        GLog.i("AppInfo", " LOG_FILE: " + LogConfig.FILE_PRIORITY);
        GLog.i("AppInfo", "*********************");
    }

}
